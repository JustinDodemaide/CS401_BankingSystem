import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class AccountService {
	public AccountService() {
		
	}
	
	public String createAccount(String[] data) {
		// data[0] is reserved for the message type
		String type = data[1];
		
		// Get valid account ID
		// Not great
		Random rand = new Random();
		String id = Integer.toString(Math.abs(rand.nextInt()));
		String filePath = "Account" + id;
		File accountFile = new File(filePath);
		while(accountFile.exists()) {
			// Keep trying random numbers until you find one thats not taken
			id = Integer.toString(Math.abs(rand.nextInt()));
			filePath = "Account" + id;
			accountFile = new File(filePath);
		}

		// When unique id is found, create new file for the account
		try {
			accountFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Add information to the account file
		// Account data is stored in one line, separated by '/'
		String line = id + "/" + type + "/" + "0.0";
		FileWriter writer = null;
		try {
			writer = new FileWriter(accountFile);
		} catch (IOException e) {
			System.err.println("Unable to write to account file");
			e.printStackTrace();
		}
		try {
			writer.write(line);
		} catch (IOException e) {
			System.err.println("Unable to write to account file");
			e.printStackTrace();
		}
		
		// Close writer
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Return account information so it can be sent as message
		return line;
	}
	
	public String getAccountData(String[] data) {
		String id = data[1];
		String path = "Account" + id;
		File accountFile = new File(path);
		if(!accountFile.exists()) {
			System.err.println("Attempting to get information from account that doesn't exist");
			return "failed";
		}
		
		Scanner scanner;
		try {
			scanner = new Scanner(accountFile);
		} catch (FileNotFoundException e) {
			System.err.println("Unable to read account file");
			e.printStackTrace();
			return "failed";
		}
		// Account data is stored in one line
		String accountData = scanner.nextLine();
		scanner.close();
				
		return accountData;
	}
	
	public void updateAccount(String[] data) {
		String id = data[1];
		String type = data[2];
		String amount = data[3];
		
		String path = "Account" + id;
		File accountFile = new File(path);
		if(!accountFile.exists()) {
			System.err.println("Attempting to update account that doesn't exist");
			return;
		}
		
		String line = id + "/" + type + "/" + amount;
		FileWriter writer = null;
		try {
			writer = new FileWriter(accountFile);
		} catch (IOException e) {
			System.err.println("Unable to write to account file");
			e.printStackTrace();
			return;
		}
		try {
			writer.write(line);
		} catch (IOException e) {
			System.err.println("Unable to write to account file");
			e.printStackTrace();
		}
		
		// Close writer
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeAccount(String[] data) {
		String id = data[1];
		String path = "Account" + id;
		File accountFile = new File(path);
		accountFile.delete();
	}
}
