import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class User {
	private String username;
	private String pin;
	private ArrayList<Account> accounts;
	private int numAccounts;
	private String sourceName;					// Filename for saving data
	
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
	
	public void saveData() throws IOException {
		File path = new File(sourceName);
		FileWriter writer = new FileWriter(path);
		for (int i = 0; i < numAccounts; i++) {
			writer.write(accounts.toString() + "\n");
		}
		writer.flush();
		writer.close();
		return;
		// Save the username, pin, and IDs of each account in the user file
		// then for each of the accounts in accounts[],
		// call .data() and store that in an account file
	}
}
