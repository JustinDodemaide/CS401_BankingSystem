import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	public Client(){
	
	}
	
	public boolean connectToServer() {
		// Connect to server
		try {
			socket = new Socket("localhost", 12345);
		} catch (UnknownHostException e) {
			System.err.println("Unable to connect to server");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.err.println("Unable to connect to server");
			e.printStackTrace();
			return false;
		}
		
		// Initialize input/output streams
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return true;
	}
	
	public void disconect() {
		if(socket == null) {
			System.out.println("Attempting to disconnect while not connected to server");
			return;
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println("Unable to close server socket");
			e.printStackTrace();
		}
	}
	
	private String[] sendMessage(String message) {
		out.println(message);

		System.out.println("awaiting response");
		String response = "";
		try {
			response = in.readLine();
		} catch (IOException e) {
			System.err.println("Unable to read server reply");
			e.printStackTrace();
		}
		
		System.out.println("response: " + response);
		// Parse response
		String[] tokens = parseMessage(response);
		return tokens;
	}
	
	public boolean attemptLogin(String username, String password) {
		String loginMessage = createMessage(new String[]{"authenticateUser",username,password});
		String[] response = sendMessage(loginMessage);
		
		final int STATUS = 0;
		if(response[STATUS].equals("user not found"))
			return false;
		
		ArrayList<String> ids = new ArrayList<String>();
		for(int i = 1; i < response.length; i++)
			ids.add(response[i]);
		
		// If user exists and pw was correct, initialize user
		StateMachine.user = new User(username,password,ids);
		return true;
	}
	
	public boolean attemptNewUser(String username, String password) {
		String newUserMessage = createMessage(new String[]{"adduser",username,password});
		String[] response = sendMessage(newUserMessage);
		
		final int STATUS = 0;
		if(response[STATUS].equals("failed"))
			return false;
		
		// If new user was created, initialize user
		StateMachine.user = new User(username,password,new ArrayList<String>());
		System.out.println("successfully made new user");
		return true;
	}
	
	public Account getAccount(String id) {
		// command: getaccount
		// Expect response: id, type, amount
		String getAccountMessage = createMessage(new String[]{"getaccount",id});
		String[] response = sendMessage(getAccountMessage);
		
		Account account = new Account(response[0],response[1],Double.parseDouble(response[2]));
		return account;
	}
	
	public Account newAccount(String type) {
		// command: newaccount
		String newAccountMessage = createMessage(new String[]{"newaccount",type});
		String[] response = sendMessage(newAccountMessage);
		// expected response: id, type, "0.0";
		
		Account newAccount = new Account(response[0],response[1],Double.parseDouble(response[2]));
		return newAccount;
	}
	
	public void updateAccount(Account account) {
		// Called by accounts when they're changed by MainControl action (deposit, transfer, ...)
		// command: updateaccount
		String updateAccountMessage = createMessage(new String[]{"updateaccount",account.getID(),account.getType(),Double.toString(account.getTotal())});
		sendMessage(updateAccountMessage); // Response isn't needed
	}
	
	private String createMessage(String[] tokens) {
		String message = "";
		for(String s : tokens) {
			message += s + ",";
		}
		return message;
	}
	
	private String[] parseMessage(String message) {
		return message.split(",");
	}
}
