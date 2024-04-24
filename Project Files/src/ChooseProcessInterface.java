import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
        panel.setBorder(BorderFactory.createTitledBorder("Options"));
        
        JButton atm = new JButton("ATM");
		atm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StateMachine.process = StateMachine.processes.ATM;
				System.out.println("ATM pressed");
			}
		});
        panel.add(atm);
        
        JButton teller = new JButton("Teller");
		atm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StateMachine.process = StateMachine.processes.TELLER;
				System.out.println("Teller pressed");
			}
		});
        panel.add(teller);
    }
	
	public void enter() {
		frame.setVisible(true);
	}
	
	public void exit() {
		frame.setVisible(false);
	}
}
