import java.util.Scanner;

public class AccountService {
    private Account[] accounts; // Array to store Account objects
    private int nextIndex; // Index to keep track of the next available position in the array
    private Scanner scanner; // Scanner object for user input
    
    public AccountService(int capacity) {
        this.accounts = new Account[capacity];
        this.nextIndex = 0;
        this.scanner = new Scanner(System.in);
    }
    
    // Method to create an account with the given customerId
    public void createAccount(String customerId) {
        if (nextIndex < accounts.length) {
            // Generating a unique accountId (you might want to implement a more robust ID generation mechanism)
            String accountId = generateAccountId();
            
            // Creating a new Account object
            Account account = new Account(accountId, customerId);
            
            // Adding the account to the array
            accounts[nextIndex] = account;
            nextIndex++;
            
            System.out.println("Account created successfully. Account ID: " + accountId);
        } else {
            System.out.println("Cannot create account. Array is full.");
        }
    }
    
    // Method to update the customerId associated with the given accountId
    public void updateAccount(String accountId, String newCustomerId) {
        for (int i = 0; i < nextIndex; i++) {
            if (accounts[i].getAccountId().equals(accountId)) {
                // Updating the customerId associated with the given accountId
                accounts[i].setCustomerId(newCustomerId);
                
                System.out.println("Account updated successfully.");
                return;
            }
        }
        System.out.println("Account does not exist.");
    }
    
    // Method to close the account associated with the given accountId
    public void closeAccount(String accountId) {
        for (int i = 0; i < nextIndex; i++) {
            if (accounts[i].getAccountId().equals(accountId)) {
                // Removing the account from the array by shifting elements
                for (int j = i; j < nextIndex - 1; j++) {
                    accounts[j] = accounts[j + 1];
                }
                accounts[nextIndex - 1] = null; // Set the last element to null
                nextIndex--;
                
                System.out.println("Account closed successfully.");
                return;
            }
        }
        System.out.println("Account does not exist.");
    }
    
    // A simple method to generate a unique accountId (you might want to implement a more robust ID generation mechanism)
    private String generateAccountId() {
        return "AA-11" + (nextIndex + 1);
    }
    
    // Method to display menu and handle user inputs
    public void displayMenu() {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Create Account");
            System.out.println("2. Update Account");
            System.out.println("3. Close Account");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter customer ID: ");
                    String customerId = scanner.nextLine();
                    createAccount(customerId);
                    break;
                case 2:
                    System.out.print("Enter account ID: ");
                    String accountIdToUpdate = scanner.nextLine();
                    System.out.print("Enter new customer ID: ");
                    String newCustomerId = scanner.nextLine();
                    updateAccount(accountIdToUpdate, newCustomerId);
                    break;
                case 3:
                    System.out.print("Enter account ID to close: ");
                    String accountIdToClose = scanner.nextLine();
                    closeAccount(accountIdToClose);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
    
    

