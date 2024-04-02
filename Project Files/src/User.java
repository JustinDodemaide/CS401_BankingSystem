
public class User {
	private String username;
	private String pin;
	private Account[] accounts;
	
	public User(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void deposit(Account account, double amount) {
		
	}
	
	public void withdraw(Account account, double amount) {
		
	}
	
	public void transfer(Account account, double amount) {
		
	}
	
	public void addAccount(Account account) {
		
	}
	
	public void removeAccount(Account account) {
		
	}
	
	public void saveData() {
		// Save the username, pin, and IDs of each account in the user file
		// then for each of the accounts in accounts[],
		// call .data() and store that in an account file
	}
}
