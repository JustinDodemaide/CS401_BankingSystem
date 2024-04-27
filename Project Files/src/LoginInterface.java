import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginInterface implements State {
    private JFrame frame = new JFrame();
	
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel warningLabel = new JLabel();
    
    private JPanel tellerPanel;
    
    public LoginInterface() {
		frame.setTitle("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
		// ATM Options
    	JPanel atmPanel = new JPanel();
    	atmPanel.setLayout(new GridLayout(0, 1)); // interface is set up in a single column
        JLabel usernameLabel = new JLabel("Username");
        usernameField = new JTextField(); // a text field for users to input their username
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(); // a password field for users to input password
        JButton loginButton = new JButton("Login");
        atmPanel.add(usernameLabel);
        atmPanel.add(usernameField);
        atmPanel.add(passwordLabel);
        atmPanel.add(passwordField);
        atmPanel.add(loginButton);
        warningLabel.setVisible(false);
        warningLabel.setForeground(Color.red);
        atmPanel.add(warningLabel);
        
        // Teller elements
        tellerPanel = new JPanel();
        tellerPanel.setLayout(new GridLayout(0, 1));
        tellerPanel.add(new JSeparator());
        JButton makeUserButton = new JButton("Make New User");
        tellerPanel.add(makeUserButton);
        
        // Put both into one panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createTitledBorder("Login"));
        panel.add(atmPanel);
        panel.add(tellerPanel);
        
        // Add panel to frame
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String username = usernameField.getText(); // validates input
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                
                if(StateMachine.client.attemptLogin(username,password)) {
                	// Credentials are correct, user has been logged in
                	StateMachine.transitionTo(StateMachine.stateNames.MAIN_CONTROLS);
                	return;
                }
                
                // Credentials are incorrect
                // Display warning/error, clear fields
                warningLabel.setText("Unable to login");
                warningLabel.setVisible(true);
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        
        makeUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                
                // Check for illegal characters
                if(username.isBlank() || password.isBlank() || username.contains(",") || password.contains(",")) {
                    warningLabel.setText("\',\' not allowed");
                    warningLabel.setVisible(true);
                    usernameField.setText("");
                    passwordField.setText("");
                    return;
                }
                
            	if(StateMachine.client.attemptNewUser(username,password)){
            		// New user was successfully created and logged in
            		StateMachine.transitionTo(StateMachine.stateNames.MAIN_CONTROLS);
                	return;
            	}
                
                warningLabel.setText("Unable to make new user");
                warningLabel.setVisible(true);
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        
    }

    public void enter() {
    	frame.setVisible(true);
    	frame.setSize(1200, 500);
    	
    	// If process is Teller, unhide the "make new user" option
    	tellerPanel.setVisible(StateMachine.tellerProcess);
    }

    public void exit() {
        warningLabel.setVisible(false);
    	frame.setVisible(false);
    }
}
