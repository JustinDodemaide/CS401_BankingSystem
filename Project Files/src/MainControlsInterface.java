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
	private JPanel accountPanel = new JPanel();;
	private ArrayList<JPanel> accountPanelItems = new ArrayList<JPanel>();
	
	public MainControlsInterface() {
		frame.setVisible(false);
		frame.setTitle("Welcome, user");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        addButtons(buttonPanel);


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
		// TODO
		/*
		for(Account account : StateMachine.user.getAccounts()) {
			JPanel item = newAccountPanelItem(account);
			accountPanelItems.add? push?(item);
			accountPanel.add(item);
		}
		*/
		// TODO
		if(accountPanelItems.isEmpty()) {
			JOptionPane.showMessageDialog(frame,  "No accounts available/found", "No Accounts", JOptionPane.INFORMATION_MESSAGE);
			// Make "no accounts available" panel?
		}
	}
	
    private JPanel newAccountPanelItem(Account account) {
    	// TODO
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
    
    private void addButtons(JPanel buttonPanel) {
        JButton deposit = new JButton("Deposit");
		deposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Deposit pressed");
				String total = JOptionPane.showInputDialog("Enter amount:");
				double amount = Double.parseDouble(total);
				StateMachine.user.deposit(null, amount);
			}
		});
        buttonPanel.add(deposit);
        
        JButton withdraw = new JButton("Withdraw");
		withdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Withdraw pressed");
			}
		});
        buttonPanel.add(withdraw);
        
        JButton transfer = new JButton("Transfer");
		transfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("Transfer pressed");
			}
		});
        buttonPanel.add(transfer);
        
        // TODO
        // Only add these options if a teller is present!
        //if(StateMachine.processes != StateMachine.processes.teller)
        //	return;
        JButton newAccount = new JButton("New Account");
		newAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("newAccount pressed");
			}
		});
        buttonPanel.add(newAccount);
        
        JButton deleteAccount = new JButton("Delete Account");
		deleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.out.println("deleteAccount pressed");
			}
		});
        buttonPanel.add(deleteAccount);    
        
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
