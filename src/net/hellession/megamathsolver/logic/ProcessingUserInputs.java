package net.hellession.megamathsolver.logic;

import net.hellession.megamathsolver.exception.SyntaxException;
import net.hellession.megamathsolver.math.Expression;

public class ProcessingUserInputs {

	/**
	 * Should process and work with the user input.
	 * Arguments: 
	 * @param Input - What the user inputted.
	 * @param Mode - What mode the user is in
	 */
	public static UserMode processInput(String Input, int Mode){
		UserMode toReturn = new UserMode();
		if (Mode == 1) {
			if (Input == "1"){
				toReturn.mode = 2;
				toReturn.userOutput = "Please input your expression. Note that fractions have a special format.";
			}
		}
		if (Mode == 2) {
			try{
			Expression tww;
			tww = Processer.parseMathematicallyExp(Input);
			toReturn.userOutput = "Your expression " + Input + " has been solved with the result " + Processer.solveExpression(tww);
			toReturn.mode = 1;
			}catch(SyntaxException e){
				toReturn.userOutput = "An error has been encountered, while processing your input: ";
				toReturn.problem = e;
			}
		}
		return toReturn;
	}
}
