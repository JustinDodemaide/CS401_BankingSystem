package project;


import java.util.Scanner;

public class AccountService {
    private Account[] accounts; // an array to keep track of accounts
    private int nextIndex; //index that keeps track of the next available position of the array
    private Scanner scanner; // scanner for user's input

    public AccountService(int capacity) {
        this.accounts = new Account[capacity];
        this.nextIndex = 0;
        this.scanner = new Scanner(System.in);
    }

    public void createAccount(String customerId) {
        if (nextIndex < accounts.length) {
            String accountId = generateAccountId(); // generates unique account ids 
            Account account = new Account(accountId, customerId); // new account object
            accounts[nextIndex] = account;
            nextIndex++;
            System.out.println("Account created successfully. Account ID: " + accountId);// when account is created successful
            // it gives this message with the new account id
        } else {
            System.out.println("Cannot create account. Array is full.");
        }
    }	// if array is full it shows an error message 

    public void updateAccount(String accountId, String newCustomerId) {
        for (int i = 0; i < nextIndex; i++) {
            if (accounts[i].getAccountId().equals(accountId)) {
                accounts[i].setCustomerId(newCustomerId);
                System.out.println("Account updated successfully.");
                return;
            }
        }
        System.out.println("Account does not exist.");
    }

    public void closeAccount(String accountId) {
        for (int i = 0; i < nextIndex; i++) {
            if (accounts[i].getAccountId().equals(accountId)) {
                for (int j = i; j < nextIndex - 1; j++) {
                    accounts[j] = accounts[j + 1];
                }
                accounts[nextIndex - 1] = null;
                nextIndex--;
                System.out.println("Account closed successfully.");
                return;
            } // once customer enters their option to delete their account, it closes the account
            	// and display a sucessfull message
        }
        System.out.println("Account does not exist.");
    }

    private String generateAccountId() {
        return "AA-01" + (nextIndex + 1);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Create Account"); // menu to for options 
            System.out.println("2. Update Account");
            System.out.println("3. Close Account");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter customer ID: "); // allows the user to inout their customer id
                    String customerId = scanner.nextLine(); // scans the line 
                    createAccount(customerId);
                    break;
                case 2:
                    System.out.print("Enter account ID: ");
                    String accountIdToUpdate = scanner.nextLine();
                    System.out.print("Enter new customer ID: ");
                    String newCustomerId = scanner.nextLine();
                    updateAccount(accountIdToUpdate, newCustomerId);
                    break;
                ca

