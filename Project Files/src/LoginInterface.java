import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginInterface extends JPanel implements State {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginInterface() {
        setLayout(new GridLayout(6, 1)); // interface is set up in a single column

        JLabel usernameLabel = new JLabel("Username");
        usernameField = new JTextField(); // a text field for users to input their username
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(); // a password field for users to input password
        JPanel createAccountPanel = new JPanel();
        JLabel createAccountLabel = new JLabel("Create Account"); // a space for creating an account
        createAccountPanel.add(createAccountLabel);
        JButton loginButton = new JButton("Login");

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(createAccountPanel);
        add(loginButton);
        // adding components to panel
       
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String username = usernameField.getText(); // validates input
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);

                enter(); // once password is entered and is verified
                // move to the main control interface

            }
        });
    }

    public void enter() {
        setVisible(false); // hides the log in interface once they hit enter

        StateMachine.showMainControls(); // moves on to the main control once login is successful
    }

    public void exit() {

    }
}
