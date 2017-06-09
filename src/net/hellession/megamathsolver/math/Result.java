package net.hellession.megamathsolver.math;

import net.hellession.megamathsolver.util.Util;

public class Result {

	
	/**
	 * 1 = The result is in form of just a NumberMonomial
	 * 2 = The result is in form of just an OperatorGroup
	 * 3 = The result is in form of a Fraction
	 * 4 = The result is complicated and is made up of few OperatorGroups
	 * 5 = The result is a NumberMonomial containing a Sqrt
	 * 6 = The result solves an Equation
	 * 7 = The result solves an Equation with more than 1 unknown numbers
	 */
	public int type;
	/**
	 * Should only exist and be initialised if type = 1
	 */
	public NumberMonomial resultMonomial;
	 /**
	  * Should only exist and be initialised if type = 2; 4
	  */
	public OperatorGroup resultOG;
	
	public Result(int typ, NumberMonomial result){
		if (typ!=1){
			throw new IllegalArgumentException("The incorrect constructor has been called in Result object, type " + type + " while being called with a NumberMonomial");
		}else{
			this.resultMonomial = result;
			this.type = typ;
		}
		
	}
	
	@Override
	public String toString(){
		String toReturn = null;
		if (this.type==1){
			toReturn = Integer.toString((int) this.resultMonomial.Number) + Util.turnMultiplierstoString(this.resultMonomial);
		}
		return toReturn;
	}
}
