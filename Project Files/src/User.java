
public class User {
	private String username;
	private String pin;
	private Account[] accounts;
	private int numAccounts;

	public User(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void deposit(Account account, double amount) {
		account.addToTotal(amount);
	}
	
	public void withdraw(Account account, double amount) {
		account.removeFromTotal(amount);
	}
	
	public void transfer(Account account, Account, targetAcc, double amount) {
		account.removeFromTotal(amount);
		targetAcc.addToTotal(amount);
	}
	
	public void addAccount(Account account) {
		Account[numAccounts] = new Account();
		numAccounts++;
	}
	
	public void removeAccount(Account account) {
		numAccounts--;
	}
	
	public void saveData() {
		// Save the username, pin, and IDs of each account in the user file
		// then for each of the accounts in accounts[],
		// call .data() and store that in an account file
	}
}
