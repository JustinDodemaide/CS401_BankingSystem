
public class StateMachine {
	private static State[] states = new State[3];
	private static State currentState = states[0];
	public enum stateNames{CHOOSE_PROCESS,LOGIN,MAIN_CONTROLS};
	
	public processes process;
	public enum processes{};
	
	public ServerClient serverClient;
	public User user;
	
	public static void main(String[] args) {
		states[0] = new ChooseProcessInterface();
		states[1] = new LoginInterface();
		states[2] = new MainControlsInterface();
		
		currentState = states[0];
		currentState.enter();
	}
	
	public static void transitionTo(stateNames targetState) {
		currentState.exit();
		currentState = states[targetState.ordinal()];
		currentState.enter();
		/* Returns the ordinal of this enumeration constant 
		(its position in its enum declaration, where the 
		initial constant is assigned an ordinal of zero */
	}
}
