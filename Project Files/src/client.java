
import java.io.*; // Import the IO classes for handling input and output streams.
import java.net.*; // Import the networking classes for socket communication.
import java.util.Scanner; // Import the Scanner class to read input from the console.

public class client {
    private static final String SERVER_IP = "127.0.0.1"; // Server IP address, "localhost" in this case.
    private static final int SERVER_PORT = 12345; // Port number on which the server is listening.

    public static void main(String[] args) {
        // Try-with-resources statement to ensure proper closure of resources.
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT); // Establish a connection to the server.
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream()); // Create an output stream to send data to the server.
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream()); // Create an input stream to receive data from the server.
             Scanner scanner = new Scanner(System.in)) { 

            System.out.println("Connected to server"); // Indicate successful connection to the server.


    } catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
