import java.util.Scanner;

import net.hellession.megamathsolver.exception.SyntaxException;
import net.hellession.megamathsolver.logic.ProcessingUserInputs;
import net.hellession.megamathsolver.logic.UserMode;
import net.hellession.megamathsolver.util.State;


/**
 * 
 * @author Hellession
 * Main
 */
public class MegaMathSolver {

	
	public static String equation;
	public static boolean endRequested;
	public static String userInput;
	private static UserMode ourMode = new UserMode();
	
	
	
	
	public static Scanner Keyboard = new Scanner(System.in);
	public static final String VERSION = "0.3.0.6";
	public static final State STATE = State.DEVELOPMENT;
	
	
	public static void main(String[] args) throws SyntaxException {
		System.out.println("Hello. Welcome to the Mathematical solver by Hellession. Version: v" + VERSION + ". Initializing...");
		try{
		preinitcheck();
		init();
		loop();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	
	public static void preinitcheck(){
		if (STATE == State.ERR){
			System.err.println("This version is marked as incompatible and should not be used. Proceed with warning.");
		}
		if (STATE == State.DEVELOPMENT){
			System.out.println("This version might have a lot of bugs and undone stuff, be careful!");
		}
	}

	/**
	 * Initializes the program by setting values/fields/properties to default.
	 */
	public static void init(){
		equation = null;
		endRequested = false;
	}
	
	/**
	 * A method, that holds the main "brain" of the program. It will loop constantly until the "endRequested" variable will be "true".
	 * @throws SyntaxException 
	 */
	public static void loop() throws SyntaxException{
		do{
			
			System.out.println(ourMode.userOutput);
			userInput = Keyboard.nextLine();
			TransferUserMode(ProcessingUserInputs.processInput(userInput, ourMode.mode));
		}while(endRequested==false);
	}
	
	/**
	 * Will set the current UserMode object within the class to the UserMode that is passed with the argument.
	 */
	private static void TransferUserMode(UserMode userMode){
		ourMode.mode = userMode.mode;
		ourMode.userOutput = userMode.userOutput;
	}
}
