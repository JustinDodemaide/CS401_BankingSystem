//private BankServer server;
import java.io.*; // Imports the IO classes for handling input and output streams.
import java.net.*; // Imports the networking classes for socket communication.
import java.util.concurrent.*; // Imports concurrency utilities, particularly for managing threads.

public class Server {
		private static AuthenticationService authenticationService = new AuthenticationService();
		private static AccountService accountService = new AccountService();
	
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
	        private PrintWriter out;      // For writing responses to the client
	        private BufferedReader in;    // For reading requests from the client

	        public ClientHandler(Socket socket) { // Constructor for ClientHandler.
	            this.clientSocket = socket; // Initializes the clientSocket with the socket for the connected client.
	        }
	        
	        @Override
	        public void run() {
	            try {
	                out = new PrintWriter(clientSocket.getOutputStream(), true);
	                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

	                // Read client inputs until the connection is closed
	                String inputLine;
	                while ((inputLine = in.readLine()) != null) {
	                    System.out.println("Received: " + inputLine); // Log received command
	                    processInput(inputLine);  // Process the received command
	                }
	            } catch (IOException e) {
	                System.out.println("Exception in ClientHandler: " + e.getMessage());
	                e.printStackTrace();
	            } finally {
	                try {
	                    closeConnections(); // Close all connections when done
	                } catch (IOException e) {
	                    System.out.println("Error when closing connection: " + e.getMessage());
	                }
	            }
	        }
	        
	        
	        
	        private void processInput(String inputLine) {
	            String[] tokens = inputLine.split(",");
	            String command = tokens[0].toLowerCase();
	            System.out.println(command);

	            try {
	                switch (command) {
	                    case "authenticateuser":
	                        handleAuthenticateUser(tokens);
	                        break;
	                    case "adduser":
	                        handleAddUser(tokens);
	                        break;
	                    case "updateuser":
	                        handleUpdateUser(tokens);
	                        break;
	                    case "newaccount":
	                        handleNewAccount(tokens);
	                        break;
	                   case "getaccount":
	                        handleGetAccount(tokens);
	                        break;
	                    case "updateaccount":
	                        handleUpdateAccount(tokens);
	                        break;
	                    case "removeaccount":
	                        handleRemoveAccount(tokens);
	                        break;   
	                    default:
	                        out.println("Error: Unknown command");
	                        break;
	                }
	            } catch (Exception e) {
	                out.println("Error processing command: " + e.getMessage());
	                e.printStackTrace();
	            }
	        }
	        
	        //Login command processing
	        private void handleAuthenticateUser(String[] tokens) {
	        	// 0 is reserved for the command
	        	final int USERNAME = 1;
	        	final int PW = 2;
	        	boolean success = authenticationService.authenticateUser(tokens[USERNAME],tokens[PW]);
	        	String message;
	        	if(success)
	        		message = "user found";
	        	else
	        		message = "user not found";
	        	out.println(message);
	        }
	        
	        //update command processing
	        private void handleAddUser(String[] tokens) {
	        	// 0 is reserved for the command
	        	final int USERNAME = 1;
	        	final int PW = 2;
	        	boolean success = authenticationService.makeNewUserAttempt(tokens[USERNAME],tokens[PW]);
	        	String message;
	        	if(!success)
	        		message = "failed";
	        	else
	        		message = "user created";
	        	out.println(message);	        
	        }
	        
	        
	        //remove account
	        private void handleUpdateUser(String[] tokens) {
	        	// TODO
	        }
	        
	        private void handleNewAccount(String[] tokens) {
	        	String accountData = accountService.createAccount(tokens);
	        	
	        	String message = accountData;
	        	out.println(message);
	        }
	        
	        private void handleGetAccount(String[] tokens) {
	        	// Get account data (type, amount) from file
	        	String data = accountService.getAccountData(tokens);
	        	String message;
	        	if(data.equals("failed"))
	        		message = "failed";
	        	// If successful, send message with data
	        	message = data;
	        	out.println(message);
	        }
	        
	        private void handleUpdateAccount(String[] tokens) {
	        	accountService.updateAccount(tokens);
	        }
	        
	        private void handleRemoveAccount(String[] tokens) {
	        	accountService.closeAccount(tokens);
	        }
	        
	        private void closeConnections() throws IOException {
	            if (out != null) out.close();
	            if (in != null) in.close();
	            if (clientSocket != null) clientSocket.close();
	        }
	    }
	}
