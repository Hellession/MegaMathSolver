package net.hellession.megamathsolver.math;

/**
 * Class for defining any monomials - numbers with multipliers. They can have a list of multipliers.
 * Multipliers can range from being just an unknown letter up to square roots.
 * @author PC
 * 
 */
public class NumberMonomial {

	/**
	 * Represents the number in a monomial, if the monomial is 4a for example, the "Number" will be 4.
	 */
	public double Number = 1.0;
	/**
	 * Represents a list of Multiplier objects.
	 * Some objects may be linked to an OperatorGroup object, if they are for example (4x + 7).
	 * In examples 6x, 9b, 15d and 2x^2, the Multipliers will be x, b, d and x^2.
	 * 
	 */
	public Multiplier[] Multipliers;
	/**
	 * The boolean will be true, if the monomial coefficient is negative: -6d, -43.2a.
	 */
	public boolean isNegative = false;
	/**
	 * Determines the power of a number, NOT any of its own Multipliers.
	 * IF a Multiplier of a Monomial also has a power, it should be determined in that Multiplier.
	 */
	public int pow = 1;
	/**
	 * The boolean will be true, if the monomial's coefficient is a fraction(BUT NONE OF THE MULTIPLIERS ARE IN A COEFFICIENT):
	 * (3/4)x
	 */
	public boolean isCoeffFraction = false;
	/**
	 * If the isCoeffFraction parameter is true, this var should be equal to the coefficient's fraction's numerator.
	 */
	public double NumeratorNumber;
	/**
	 * If the isCoeffFraction parameter is true, this var should be equal to the coefficient's fraction's denominator.
	 */
	public double DenominatorNumber;
	
	
	public NumberMonomial(){
		
	}
	
	public NumberMonomial(char digit){
		if (Character.isDigit(digit)){
		this.Number = Character.digit(digit, 10);
		}else{
			this.Number = 1;
			this.addMultiplier(new Multiplier(this, digit));
		}
	}
	
	
	public Multiplier getLastMulti(){
		Multiplier toReturn = null;
		if (this.Multipliers.length == 0){
			throw new NullPointerException("Failed to locate a Multiplier in a single type list");
		}else{
			toReturn = this.Multipliers[this.Multipliers.length];
		}
		return toReturn;
	}
	
	public void addMultiplier(Multiplier what){
		Multipliers[Multipliers.length+1] = what;
	}
}
