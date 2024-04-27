import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class MainControlsInterface implements State {
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame(); // Keep the UI elements loaded into memory for performance
	private JPanel accountPanel = new JPanel();
	private JPanel buttonPanel;
	
	public MainControlsInterface() {
		frame.setVisible(false);
		frame.setTitle("Welcome, user");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        addButtons();

        accountPanel.setLayout(new GridLayout(0, 1));
        accountPanel.setBorder(BorderFactory.createTitledBorder("Accounts"));
        JScrollPane accountScrollPane = new JScrollPane(accountPanel);
        
        frame.add(accountScrollPane);
        frame.add(buttonPanel);
        
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        frame.pack();
        frame.setLocationRelativeTo(null);
	}
	
	public void enter() {
		update();
        frame.setVisible(true);
        frame.setSize(600, 250);
	}

	public void exit() {
        frame.setVisible(false);
	}
	
	private void update() {
		// 1 Change welcome message (in case user changed)
		// 2 Reset the account items

		frame.setTitle("Welcome, " + StateMachine.user.getUsername());
		
		// Reset the account items
		accountPanel.removeAll();
		for(Account account : StateMachine.user.getAccounts()) {
			JPanel item = newAccountPanelItem(account);
			accountPanel.add(item);
		}

		// "No accounts available" card
		
		addButtons();
		
        frame.pack();
        frame.setLocationRelativeTo(null);
	}
	
    private JPanel newAccountPanelItem(Account account) {
    	JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new GridLayout(0,1));

        JLabel label1 = new JLabel(account.getID());
        label1.setFont(new Font("Arial", Font.BOLD, 16)); // Make the number stand out from the other text
        label1.setBorder(new EmptyBorder(5, 5, 0, 5)); // Add padding
        JLabel label2 = new JLabel(account.getType());
        label2.setBorder(new EmptyBorder(5, 5, 0, 5));
        JLabel label3 = new JLabel(Double.toString(account.getTotal()));
        label3.setBorder(new EmptyBorder(5, 5, 0, 5));

        accountPanel.add(label1);
        accountPanel.add(label2);
        accountPanel.add(label3);

        accountPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        
        return accountPanel;
    }
    
    private void addButtons() {
    	buttonPanel.removeAll();
    	
        JButton deposit = new JButton("Deposit");
		deposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Deposit pressed");
				String accountID = JOptionPane.showInputDialog("Enter account id:");
				Account account = StateMachine.user.getAccountFromID(accountID);
				if(account == null) {
					JOptionPane.showMessageDialog(frame, "Could not find account (" + accountID + ")", "Invalid ID", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String total = JOptionPane.showInputDialog("Enter amount:");
				double amount = Double.parseDouble(total);
				if(amount <= 0) {
					JOptionPane.showMessageDialog(frame, "Invalid Amount", "Invalid Amount", JOptionPane.INFORMATION_MESSAGE);
					return;		
				}
				StateMachine.user.deposit(account, amount);
				update();
			}
		});
        buttonPanel.add(deposit);
        
        JButton withdraw = new JButton("Withdraw");
		withdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Withdraw pressed");
				String accountID = JOptionPane.showInputDialog("Enter account id:");
				Account account = StateMachine.user.getAccountFromID(accountID);
				if(account == null) {
					JOptionPane.showMessageDialog(frame, "Could not find account (" + accountID + ")", "Invalid ID", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String total = JOptionPane.showInputDialog("Enter amount:");
				double amount = Double.parseDouble(total);
				if(amount <= 0 || amount > account.getTotal()) {
					JOptionPane.showMessageDialog(frame, "Invalid Amount", "Invalid Amount", JOptionPane.INFORMATION_MESSAGE);
					return;		
				}
				StateMachine.user.withdraw(account, amount);
				update();
			}
		});
        buttonPanel.add(withdraw);
        
        JButton transfer = new JButton("Transfer");
		transfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Transfer pressed");
				String accountID1 = JOptionPane.showInputDialog("Enter 1st account id:");
				Account account1 = StateMachine.user.getAccountFromID(accountID1);
				if(account1 == null) {
					JOptionPane.showMessageDialog(frame, "Could not find account (" + accountID1 + ")", "Invalid ID", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String accountID2 = JOptionPane.showInputDialog("Enter 2nd account id:");
				Account account2 = StateMachine.user.getAccountFromID(accountID2);
				if(account2 == null) {
					JOptionPane.showMessageDialog(frame, "Could not find account (" + accountID2 + ")", "Invalid ID", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String total = JOptionPane.showInputDialog("Enter amount:");
				double amount = Double.parseDouble(total);
				if(amount <= 0 || amount > account1.getTotal()) {
					JOptionPane.showMessageDialog(frame, "Invalid Amount", "Invalid Amount", JOptionPane.INFORMATION_MESSAGE);
					return;		
				}
				StateMachine.user.withdraw(account1, amount);
				StateMachine.user.deposit(account2, amount);
				update();
			}
		});
        buttonPanel.add(transfer);
        
        // Only add these options if a teller is present!
        if(StateMachine.tellerProcess) {
	        JButton newAccount = new JButton("New Account");
			newAccount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String type = "checking";
					String[] commands = {"Checking", "Saving"};
					int choice;
					choice = JOptionPane.showOptionDialog(null,
							"Select a command", 
							"Account Type", 
							JOptionPane.YES_NO_CANCEL_OPTION, 
							JOptionPane.QUESTION_MESSAGE, 
							null, 
							commands,
							commands[0]);
					
					switch (choice) {
						case 0: type = "checking"; break;
						case 1: type = "saving"; break;
						default:  // do nothing
					}
					System.out.println("choice: " + choice);
					Account newAccount = StateMachine.client.newAccount(type);
					StateMachine.user.addAccount(newAccount);
					System.out.println("newAccount pressed");
					update();
				}
			});
	        buttonPanel.add(newAccount);
	        
	        JButton deleteAccount = new JButton("Delete Account");
			deleteAccount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String accountID = JOptionPane.showInputDialog("Enter account id:");
					Account account = StateMachine.user.getAccountFromID(accountID);
					if(account == null) {
						JOptionPane.showMessageDialog(frame, "Could not find account (" + accountID + ")", "Invalid ID", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					StateMachine.user.removeAccount(account);
					StateMachine.client.removeAccount(account);
					System.out.println("deleteAccount pressed");
					update();
				}
			});
	        buttonPanel.add(deleteAccount);    
        }
        
        JButton logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("logout pressed");
				StateMachine.transitionTo(StateMachine.stateNames.LOGIN);
			}
		});
        buttonPanel.add(logout);    
    }
}
