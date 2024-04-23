import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

	private Socket socket;

	public void connectToServer() throws Exception {

		socket = new Socket("127.0.0.1", 12345);
		System.out.println("Connected to the server");
	}

	public void disconect() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void login(String username, String password) throws Exception {

		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		Message loginMessage = new Message(username, password);
		outputStream.writeObject(loginMessage);
		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
		Message loginResponse = (Message) inputStream.readObject();
		System.out.println(loginResponse);

	}

	public static void main(String[] args) {
		Client client = new Client();
		try {
			client.connectToServer();
		} catch (Exception e) {
			client.disconect();
		}

	}
}