import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserService {
	public UserService() {
		
	}
	
	public boolean attemptCreateUser(String username, String pw) {
		String filePath = "User" + username;
		File userFile = new File(filePath);
		
		if(userFile.exists())
			return false;

		try {
			userFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Add information to the user file
		// User data is stored in one line, separated by ','
		String line = username + "," + pw + ","; // Account ids follow pw
		FileWriter writer = null;
		try {
			writer = new FileWriter(userFile);
		} catch (IOException e) {
			System.err.println("Unable to write to user file");
			e.printStackTrace();
		}
		try {
			writer.write(line);
		} catch (IOException e) {
			System.err.println("Unable to write to user file");
			e.printStackTrace();
		}
		
		// Close writer
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean authenticateUser(String username, String pw) {
		String path = "User" + username;
		File accountFile = new File(path);
		if(!accountFile.exists())
			return false;
		
		Scanner scanner;
		try {
			scanner = new Scanner(accountFile);
		} catch (FileNotFoundException e) {
			System.err.println("Unable to read user file");
			e.printStackTrace();
			return false;
		}
		
		String userData = scanner.nextLine();
		scanner.close();
		
		String storedPW = userData.split(",")[1];
		if(pw.equals(storedPW))
			return true;
		return false;
	}
	
	public String getUserData(String username) {
		String path = "User" + username;
		File accountFile = new File(path);
		if(!accountFile.exists()) {
			System.err.println("Attempting to get information from user that doesn't exist");
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
		// User data is stored in one line
		String userData = scanner.nextLine();
		scanner.close();
		
		return userData;
	}
	
	public void updateUser(String username, String pw, String[] accountIDs) {	
		String path = "User" + username;
		File userFile = new File(path);
		if(!userFile.exists()) {
			System.err.println("Attempting to update user that doesn't exist");
			return;
		}
		
		String line = username + "," + pw + ",";
		for(String id : accountIDs)
			line += id + ",";
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(userFile);
		} catch (IOException e) {
			System.err.println("Unable to write to user file");
			e.printStackTrace();
			return;
		}
		try {
			writer.write(line);
		} catch (IOException e) {
			System.err.println("Unable to write to user file");
			e.printStackTrace();
		}
		
		// Close writer
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeUser(String[] data) {
		String username = data[1];
		String path = "User" + username;
		File userFile = new File(path);
		userFile.delete();
	}
}
