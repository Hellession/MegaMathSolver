package net.hellession.megamathsolver.logic;

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
				toReturn.userOutput = "Please input your expression.";
			}
		}
		if (Mode == 2) {
			
		}
		return toReturn;
	}
}
