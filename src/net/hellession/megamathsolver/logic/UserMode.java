package net.hellession.megamathsolver.logic;

public class UserMode {

	/**
	 *  1 - Select solver.
	 *  2 - Set expression.
	 *  4 - Set equation.
	 *  5 - Set rational expression.
	 *  6 - Set rational equation.
	 *  7 - Set what to simplify.
	 *  11 - Error line.
	 *  12 - Questions?
	 */
	public int mode;
	
	public String userOutput;
	public Exception problem;
	public boolean debug = false;
	
	public UserMode(){
		this.mode = 1;
		this.userOutput = "Select solver mode: 1 for expression, 2 for equation, 3 for rational expression, 4 for rational equation, 5 for simplify. Type DEBUG to toggle DEBUG mode.";
		this.problem = null;
	}
}
