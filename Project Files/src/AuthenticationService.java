import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AuthenticationService {

	private Map<String, String> loginInfoMap;

	public AuthenticationService() {
		this.loginInfoMap = new HashMap<>();
		readFromFile("loginInfo.txt");
	}

	private void readFromFile(String fileName) {
		try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.isBlank())
					continue;
				String[] parts = line.split(",");

				String username = parts[0];
				String password = parts[1];
				loginInfoMap.put(username, password);
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
	}

	public Map<String, String> getInfo() {
		return loginInfoMap;
	}
	
	public boolean authenticateUser(String username, String password) {

		if (!loginInfoMap.containsKey(username)) {
			return false;
		}

		if (loginInfoMap.get(username).equals(password)) {
			return true;
		}
		return false;
	}

	public boolean makeNewUserAttempt(String username, String password) {

		if (doesUserExist(username, password)) {
			System.out.println("There is already a user with that login information.");
			return false;
		}
		loginInfoMap.put(username, password);

		try {
			FileWriter myWriter = new FileWriter("loginInfo.txt", true);

			myWriter.write("\n" + username + "," + password + ",");

			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("New user added to file");
		return true;
	}
	
	public void updateUser(String username, String pw, String[] accountIDs) {
		// To change one line of the text file:
		// Add all the old content to a string
		// If the username matches, change the line
		// Overwrite the file with the string
		
		String userString = username + "," + pw;
		for(String id : accountIDs)
			userString += "," + id;
		
		// Add all the contents to a string, replace the necessary information
		String fileContent = "";
		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader("loginInfo.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		boolean found = false;
		while(scanner.hasNextLine()) {
			if(!fileContent.isEmpty())
				fileContent += "\n";
			String line = scanner.nextLine();
			if(!found)
				if(line.contains(username)) {
					line = userString;
					found = true;
				}
			fileContent += line;
		}
		scanner.close();
		
		System.out.println("file content: " + fileContent);

		
		// Write the contents back to the file
		FileWriter writer = null;
		try {
			writer = new FileWriter("loginInfo.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		try {
			writer.write(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean doesUserExist(String username, String password) {
		if (loginInfoMap.containsKey(username)) {
			return true;
		} else {
			return false;
		}
	}
}