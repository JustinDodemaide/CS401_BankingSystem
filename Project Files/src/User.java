import java.util.ArrayList;

public class User {
	private String username;
	private String pin;
	private ArrayList<Account> accounts = new ArrayList<Account>();
	
	public User(String username, String pin, ArrayList<String> accountIDs) {
		this.username = username;
		this.pin = pin;
		
		// for each account id, get account from client, addAccount
		System.out.println("num ids: " + accountIDs.size());
		for(String id : accountIDs) {
			System.out.println("id: " + id);
			
			Account account = StateMachine.client.getAccount(id);
			accounts.add(account);
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPIN() {
		return pin;
	}
	
	public ArrayList<Account> getAccounts(){
		return accounts;
	}
	
	public void deposit(Account account, double amount) {
		account.addToTotal(amount);
	}
	
	public void withdraw(Account account, double amount) {
		account.removeFromTotal(amount);
	}
	
	public void transfer(Account account, Account targetAcc, double amount) {
		account.removeFromTotal(amount);		// Remove amount from original account
		targetAcc.addToTotal(amount);			// Add amount to target account
	}
	
	public void addAccount(Account account) {
		accounts.add(account);					// Add account to arraylist
		StateMachine.client.updateUser(this);
	}
	
	public void removeAccount(Account account) {
		accounts.remove(account);				// Remove account from arraylist
		StateMachine.client.updateUser(this);
	}
	
	public Account getAccountFromID(String id) {
		for(Account account : accounts) {
			if(account.getID().equals(id))
				return account;
		}
		return null;
	}
	
	public String toString() {
		String newString = this.username + ", " + this.pin;
		return newString;
	}
}