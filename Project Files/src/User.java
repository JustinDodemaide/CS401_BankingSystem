import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class User {
	private String username;
	private String pin;
	private ArrayList<Account> accounts;
	private int numAccounts;
	
	public User(String username, String pin, ArrayList<String> accountIDs) {
		this.username = username;
		this.pin = pin;
		
		// for each account id, get account from client, addAccount
		// for (type variableName : arrayName)
		for(String id : accountIDs) {
			Account account = StateMachine.client.getAccount(id);
			addAccount(account);
		}
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
	
	public void transfer(Account account, Account targetAcc, double amount) {
		account.removeFromTotal(amount);		// Remove amount from original account
		targetAcc.addToTotal(amount);			// Add amount to target account
	}
	
	public void addAccount(Account account) {
		accounts.add(account);					// Add account to arraylist
		numAccounts++;
	}
	
	public void removeAccount(Account account) {
		accounts.remove(account);				// Remove account from arraylist
		numAccounts--;
	}
	
	public String toString() {
		String newString = this.username + ", " + this.pin;
		return newString;
	}
}