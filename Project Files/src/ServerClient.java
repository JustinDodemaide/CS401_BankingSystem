

	//private BankServer server;
	import java.io.*; // Imports the IO classes for handling input and output streams.
	import java.net.*; // Imports the networking classes for socket communication.
	import java.util.concurrent.*; // Imports concurrency utilities, particularly for managing threads.

	public class ServerClient {
	    private static final int PORT = 12345; // The port number on which the server listens for connections.

	    public static void main(String[] args) throws IOException {
	        ExecutorService pool = Executors.newFixedThreadPool(20); // Creates a thread pool with 20 threads for handling client connections.
	        try (ServerSocket serverSocket = new ServerSocket(PORT)) { // Opens a server socket that listens on the specified port.
	            System.out.println("Server is listening on port " + PORT); // Indicates that the server has started and is listening for connections.
	            while (true) { // An infinite loop to continuously accept new client connections.
	                Socket clientSocket = serverSocket.accept(); // Waits for a client to connect and accepts the connection.
	                System.out.println("New client connected"); // Indicates that a new client has connected to the server.
	                pool.execute(new ClientHandler(clientSocket)); // Assigns a new ClientHandler task to the thread pool to handle the client connection in a separate thread.
	            }
	        } finally {
	            pool.shutdown(); // Attempts to gracefully shutdown the thread pool when the server socket is closed or the program ends.
	            
	        }
	    }

	    private static class ClientHandler implements Runnable { // A nested static class that defines the logic for handling client connections.
	        private final Socket clientSocket; // The client socket this handler is responsible for.

	        public ClientHandler(Socket socket) { // Constructor for ClientHandler.
	            this.clientSocket = socket; // Initializes the clientSocket with the socket for the connected client.
	        }
	        
	        @Override
	        public void run() {
	        	try(ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
	        			ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())){
	        		while(true) {
	        			
	        		
	        	}
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	    }
	
	}
}
	        
	    