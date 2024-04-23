import java.io.Serializable;

public class Message implements Serializable {
	protected String username;
	protected String password;

	public Message() {
		this.username = "Undefined";
		this.password = "Undefined";
	}

	public Message(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
}