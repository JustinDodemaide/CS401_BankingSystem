import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
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
		System.out.println("1");
		
		// Initialize input/output streams
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("2");
        try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("3");
		
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
	
	public boolean attemptLogin(String username, String password) {		
		String loginMessage = createMessage(new String[]{"authenticateUser",username,password});
		
		out.println(loginMessage);

		String loginResponse = "";
		try {
			loginResponse = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("login response: " + loginResponse);
		// Parse response
		String[] tokens = parseMessage(loginResponse);
		
		final int STATUS = 0;
		if(tokens[STATUS].equals("user not found"))
			return false;
		
		// If user exists and pw was correct, initialize user
		StateMachine.user = new User(username,password);
		return true;
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
