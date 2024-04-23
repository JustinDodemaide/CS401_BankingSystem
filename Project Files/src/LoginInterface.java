import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginInterface implements State {
    private JFrame frame = new JFrame();
	
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    public LoginInterface() {
		frame.setTitle("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1)); // interface is set up in a single column

        JLabel usernameLabel = new JLabel("Username");
        usernameField = new JTextField(); // a text field for users to input their username
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(); // a password field for users to input password
        JPanel createAccountPanel = new JPanel();
        JLabel createAccountLabel = new JLabel("Create Account"); // a space for creating an account
        createAccountPanel.add(createAccountLabel);
        JButton loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(createAccountPanel);
        panel.add(loginButton);
        // adding components to panel
        
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String username = usernameField.getText(); // validates input
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                
                // once password is entered and is verified
                // move to the main control interface

            }
        });
    }

    public void enter() {
    	frame.setVisible(true);

        // StateMachine.showMainControls(); // moves on to the main control once login is successful
    }

    public void exit() {
        // setVisible(false); // hides the log in interface once they hit enter

    }
}
