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
	private String userFile;					// Filename for saving user data
	private String accountFile;					// Filename for saving account data
	
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
	
	public String toString() {
		String newString = this.username + ", " + this.pin;
		return newString;
	}
	
	public void saveData() throws IOException {
		File path1 = new File(userFile);
		FileWriter writer1 = new FileWriter(path1);
		for (int i = 0; i < numAccounts; i++) {
			writer1.write(toString() + ", " + accounts.get(i).getID() + "\n");	// Write username, pin, and
		}																					// accountID into user file
		writer1.flush();
		writer1.close();
		
		File path2 = new File(userFile);
		FileWriter writer2 = new FileWriter(path2);
		for (int i = 0; i < numAccounts; i++) {
			writer2.write(accounts.get(i).toString() + "\n");	// Write each account into account file in form:
		}							// id, type, total
		writer2.flush();
		writer2.close();
		return;
		// Save the username, pin, and IDs of each account in the user file
		// then for each of the accounts in accounts[],
		// call .data() and store that in an account file
	}
}
