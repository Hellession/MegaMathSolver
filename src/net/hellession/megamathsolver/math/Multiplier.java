package net.hellession.megamathsolver.math;

import net.hellession.megamathsolver.logic.Logger;

/**
 * That something, which will store information about a monomial's multiplier. Is always paired to a Fraction or a Number.
 * @author PC
 * 
 */
public class Multiplier {
	public int MultiType;
	public char MultiChar;
	public int pow = 1;
	public NumberMonomial boundTo;
	
	public Multiplier(NumberMonomial set){
		this.boundTo = set;
	}
	
	public Multiplier(NumberMonomial set, char charToSet){
		this.boundTo = set;
		this.MultiChar = charToSet;
	}
	
	@Override
	public String toString(){
		Logger.logDebugInfo("toString() of Multiplier was called");
		return Character.toString(this.MultiChar);
	}
}
