import java.util.Scanner;

import net.hellession.megamathsolver.logic.ProcessingUserInputs;
import net.hellession.megamathsolver.logic.UserMode;
import net.hellession.megamathsolver.util.State;


/**
 * 
 * @author Hellession
 * Main
 */
public class MegaMathSolver {

	/**
	 * These below are called "fields". They can also be called "properties of the class". These are variables I set to work with.
	 * Remember that there are words in the purple color. Those are keywords and they hold special meaning.
	 */
	public static String equation;
	public static boolean endRequested;
	public static String userInput;
	private static UserMode ourMode = new UserMode();
	
	
	public static Scanner Keyboard = new Scanner(System.in);
	public static final String VERSION = "0.2.1";
	public static final State STATE = State.DEVELOPMENT;
	
	/**
	 * This below is called a "method". "Methods" are also called "functions" in other programming languages. They do something, then give you a
	 * result. Let's say you need to add 2 numbers. You can make a method that will add 2 numbers and then use "return" keyword to return the
	 * result. Methods are a really important concept in programming.
	 */
	public static void main(String[] args) {
		System.out.println("Hello. Welcome to the Mathematical solver. Version: v" + VERSION + ". Initializing...");
		preinitcheck();
		init();
		loop();
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
	 */
	public static void loop(){
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
