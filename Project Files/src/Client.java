import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	
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
			toServer = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Unable to connect to server");
			e.printStackTrace();
			return false;
		}
		try {
			fromServer = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("Unable to connect to server");
			e.printStackTrace();
			return false;
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
	
	public boolean attemptLogin(String username, String password) {		
		String loginMessage = createMessage(new String[]{"login",username,password});
		try {
			toServer.writeObject(loginMessage);
		} catch (IOException e) {
			System.err.println("Unable to send message to server");
			e.printStackTrace();
			return false;
		}
		String loginResponse;
		try {
			loginResponse = (String)fromServer.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Unable to receive message from server");
			e.printStackTrace();
			return false;		
		} catch (IOException e) {
			System.err.println("Unable to receive message from server");
			e.printStackTrace();
			return false;
		}
		
		System.out.println(loginResponse);
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
