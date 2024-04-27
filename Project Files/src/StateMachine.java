
public class StateMachine {
	private static State[] states = new State[3];
	private static State currentState = states[0];
	public enum stateNames{CHOOSE_PROCESS,LOGIN,MAIN_CONTROLS};
	
	public static boolean tellerProcess = false;
	
	public static Client client = new Client();
	public static User user;
	
	public static void main(String[] args) {
		states[0] = new ChooseProcessInterface();
		states[1] = new LoginInterface();
		states[2] = new MainControlsInterface();
		
		currentState = states[0];
		currentState.enter();
		
		if(!client.connectToServer())
			System.err.println("Client unable to connect to server");
	}
	
	public static void transitionTo(stateNames targetState) {
		currentState.exit();
		currentState = states[targetState.ordinal()];
		currentState.enter();
	}
}
