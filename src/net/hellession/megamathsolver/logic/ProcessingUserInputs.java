package net.hellession.megamathsolver.logic;

import net.hellession.megamathsolver.exception.SyntaxException;
import net.hellession.megamathsolver.math.Expression;

public class ProcessingUserInputs {

	/**
	 * Should process and work with the user input.
	 * Arguments: 
	 * @param Input - What the user inputted.
	 * @param Mode - What mode the user is in
	 * @throws SyntaxException 
	 */
	public static UserMode processInput(String Input, int Mode) throws SyntaxException{
		UserMode toReturn = new UserMode();
		if (Mode == 1) {
			if (Input.equals("1")){
				toReturn.mode = 2;
				toReturn.userOutput = "Please input your expression. Note that fractions have a special format.";
			}
			if (Input.equals("DEBUG")){
				if (Values.debug==false){
					Values.debug = true;
				}else{
					Values.debug = false;
				}
				toReturn.mode = 1;
				toReturn.userOutput = "Select solver mode: 1 for expression, 2 for equation, 3 for rational expression, 4 for rational equation, 5 for simplify. Type DEBUG to toggle DEBUG mode.";
			}
		}
		if (Mode == 2) {
			Expression tww;
			tww = Processer.parseMathematicallyExp(Input);
			toReturn.userOutput = "Your expression " + Input + " has been solved with the result " + Processer.solveExpression(tww).toString();
			toReturn.mode = 1;
		}
		return toReturn;
	}
}
