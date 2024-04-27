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
	private ArrayList<JPanel> accountPanelItems = new ArrayList<JPanel>();
	
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
	}

	public void exit() {
		// Keep it loaded in memory
        frame.setVisible(false);
	}
	
	private void update() {
		// 1 Change welcome message (in case user changed)
		// 2 Reset the account items

		frame.setTitle("Welcome, " + StateMachine.user.getUsername());
		
		// Reset the account items
		accountPanelItems.clear();
		for(Account account : StateMachine.user.getAccounts()) {
			JPanel item = newAccountPanelItem(account);
			accountPanelItems.add(item);
			accountPanel.add(item);
		}

		// "No accounts available" panel
		
		addButtons();
	}
	
    private JPanel newAccountPanelItem(Account account) {
    	JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new GridLayout(0,1));

        JLabel label1 = new JLabel("Number");
        label1.setFont(new Font("Arial", Font.BOLD, 16)); // Make the number stand out from the other text
        label1.setBorder(new EmptyBorder(5, 5, 0, 5)); // Add padding
        JLabel label2 = new JLabel("Type");
        label2.setBorder(new EmptyBorder(5, 5, 0, 5));
        JLabel label3 = new JLabel("Amount");
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
				String total = JOptionPane.showInputDialog("Enter amount:");
				double amount = Double.parseDouble(total);
				StateMachine.user.deposit(StateMachine.user.getAccountFromID(accountID), amount);
			}
		});
        buttonPanel.add(deposit);
        
        JButton withdraw = new JButton("Withdraw");
		withdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Withdraw pressed");
				String accountID = JOptionPane.showInputDialog("Enter account id:");
				String total = JOptionPane.showInputDialog("Enter amount:");
				double amount = Double.parseDouble(total);
				StateMachine.user.withdraw(StateMachine.user.getAccountFromID(accountID), amount);
			}
		});
        buttonPanel.add(withdraw);
        
        JButton transfer = new JButton("Transfer");
		transfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Transfer pressed");
				String accountID1 = JOptionPane.showInputDialog("Enter 1st account id:");
				String accountID2 = JOptionPane.showInputDialog("Enter 2nd account id:");
				String total = JOptionPane.showInputDialog("Enter amount:");
				double amount = Double.parseDouble(total);
				StateMachine.user.withdraw(StateMachine.user.getAccountFromID(accountID1), amount);
				StateMachine.user.deposit(StateMachine.user.getAccountFromID(accountID2), amount);
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
						case 0: type = "checking";
						case 1: type = "saving";
						default:  // do nothing
					}
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
					StateMachine.user.removeAccount(account);
					StateMachine.client.removeAccount(account);
					System.out.println("deleteAccount pressed");
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
