import java.io.BufferedReader;
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
				String[] parts = line.split(",");

				if (parts.length == 2) {
					String username = parts[0];
					String password = parts[1];
					loginInfoMap.put(username, password);
				} else {
					System.err.println("Invalid line format: " + line);
				}
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

	public boolean doesUserExist(String username, String password) {
		if (loginInfoMap.containsKey(username)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	public static void main(String[] args) {
		AuthenticationService authService = new AuthenticationService();
		System.out.println(authService.loginAttempt("nicoSavaides", "5050"));
		System.out.println(authService.loginAttempt("taylorSwift", "8765"));
		System.out.println(authService.loginAttempt("baronDavis", "4444"));
		System.out.println();

		authService.makeNewUserAttempt("addMe", "2420");
		System.out.println("Hashmap: " + authService.getInfo());

		System.out.println(authService.doesAccountExist("baronDavis", "4444"));
		System.out.println(authService.doesAccountExist("draymondGreen", "3241"));
		authService.makeNewUserAttempt("nicoSavaides", "3330");

	}
	*/
}