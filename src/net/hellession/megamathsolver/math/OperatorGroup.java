package net.hellession.megamathsolver.math;

import java.util.Arrays;

/**
 * Class for defining a group of operators, this would be used if the operators are in brackets, then put in an order.
 * Example of this class's objects would be: (6x^2 + 4x - 2)
 * @author Hellession
 * 
 */
public class OperatorGroup {
	
	/**
	 * This determines the group of operators and monomials following each other.
	 * Please note that the order of this list also determines the order in which they are listed.
	 * On top of that, this list should always follow the [Monomial], [Operator], [Monomial], [Operator]... patern.
	 * An OperatorGroup itself can be contained in this list as well, replacing a Monomial.
	 * At the end of the list there should be a Monomial object.
	 */
	public Object[] Group;
	
	/**
	 * An OperatorGroup object, which superseeds this current object.
	 */
	public OperatorGroup lower;
	
	/**
	 * A method that can be used to retrieve Last Monomials.
	 * @return last Monomial in the OperatorGroup.
	 */
	public NumberMonomial getLastMonomial(){
		NumberMonomial toReturn = null;
		for(int i = this.Group.length;i>0;--i){
			if (Group[i] instanceof NumberMonomial){
				toReturn = (NumberMonomial) Group[i];
			}
		}
		if (toReturn == null){
			throw new NullPointerException("The array " + Arrays.toString(Group) + " did not contain any members of class NumberMonomial");
		}
		return toReturn;
	}
	
	/**
	 * A method that can be used to receive Last Operators.
	 * @return last Operator in the OperatorGroup.
	 */
	public Operator getLastOperator(){
		Operator toReturn = null;
		for(int i = this.Group.length;i>0;--i){
			if (Group[i] instanceof Operator){
				toReturn = (Operator) Group[i];
			}
		}
		if (toReturn == null){
			throw new NullPointerException("The array " + Arrays.toString(Group) + " did not contain any members of class Operator");
		}
		return toReturn;
	}
	
	/**
	 * This method will add a Monomial to the OperatorGroup.
	 * @param what - represents the NumberMonomial to add.
	 * @return will return true, if the operator was added successfully and false, if there was a problem and it was not.
	 */
	public boolean addMonomial(NumberMonomial what){
		boolean success = false;
		if (Group.length == 0){
			Group[Group.length+1] = what;
			success = true;
		}else{
			if (Group[Group.length] instanceof Operator){
				Group[Group.length+1] = what;
				success = true;
			}else{
				success = false;
			}
		}
		return success;
	}
	
	/**
	 * This method will add an Operator to the OperatorGroup.
	 * @param what - represents the Operator to add.
	 * @return will return true, if the operator was added successfully and false, if there was a problem and it was not.
	 */
	public boolean addOperator(Operator what){
		boolean success = false;
		if (Group.length == 0){
			success = false;
		}else{
			if (Group[Group.length] instanceof NumberMonomial || Group[Group.length] instanceof OperatorGroup){
				Group[Group.length+1] = what;
				success = true;
			}else{
				success = false;
			}
		}
		return success;
	}
	
	public boolean addOperatorGroup(OperatorGroup what){
		boolean success = false;
		if (Group.length == 0){
			Group[Group.length+1] = what;
			success = true;
		}else{
			Group[Group.length+1] = what;
			success = true;
		}
		return success;
	}
	
	public OperatorGroup(OperatorGroup prev){
		this.lower = prev;
	}
	
	public OperatorGroup(){ // Using this constructor is not recommended
	}
}
