import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChooseProcessInterface implements State{
	private JFrame frame = new JFrame(); // Keep the UI elements loaded into memory for performance
	
	public ChooseProcessInterface() {
		frame.setVisible(false);
		frame.setTitle("Choose Process");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createTitledBorder("Process"));
        
        JButton atm = new JButton("ATM");
		atm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("ATM chosen");
				StateMachine.transitionTo(StateMachine.stateNames.LOGIN);
			}
		});
        panel.add(atm);
        
        JButton teller = new JButton("Teller");
		teller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StateMachine.tellerProcess = true;
				System.out.println("Teller chosen");
				StateMachine.transitionTo(StateMachine.stateNames.LOGIN);
			}
		});
        panel.add(teller);
        
        frame.add(panel);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
	
	public void enter() {
		frame.setVisible(true);
		frame.setSize(600, 500);
	}
	
	public void exit() {
		frame.setVisible(false);
	}
}
