package net.hellession.megamathsolver.logic;

import net.hellession.megamathsolver.exception.SyntaxException;
import net.hellession.megamathsolver.math.*;

public class Processer {
	/**
	 * ?????
	 * @param what
	 * @return
	 */
	public static String process(String what){
		return "";
	}
	
	/**
	 * A complicated method, which parses a string(which is considered an Expression) into different objects, that this program can easily
	 * understand, then creates an Expression object for all of that.
	 * @param what - a String to parse as an Expression.
	 * @return an Expression object result.
	 * @throws SyntaxException
	 */
	public static Expression parseMathematicallyExp(String what) throws SyntaxException{
		int processMode = 0; // 0 - none, 1 - reading a Monomial(numbers)! 2 - reading a Monomial's multipliers! 3 - reading a Number's multiplier, 4 - reading a Multiplier's multiplier,
		Expression ourThing = new Expression();
		/**
		 * The Tree var is responsible for showing how many "OperatorGroup" upwards have been made.
		 * This means that if there are brackets inside of an Expression, it will increment upon entering these brackets,
		 * then once again upon entering another brackets
		 */
		int Tree = 1;
		OperatorGroup ourCurrentThing = ourThing;
		OperatorGroup ourLowerLevel = null;
		what.toLowerCase();
		for(int i=0; i < what.length() + 1; ++i){
			if (Character.isDigit(what.charAt(i)) && processMode == 0) { // A NumberMonomial with any int coefficient
				processMode = 1;
				ourCurrentThing.addMonomial(new NumberMonomial(what.charAt(i)));
				while(Character.isDigit(what.charAt(i+1)) == true){
					ourCurrentThing.getLastMonomial().Number = connectNumbers(ourCurrentThing.getLastMonomial().Number,what.charAt(i));
					++i;
				}
			}
			if (Character.isAlphabetic(what.charAt(i)) && processMode == 0){ // A NumberMonomial with coefficient 1 and a Multiplier letter
				if (ourCurrentThing.getLastMonomial().Multipliers[0].MultiChar == 's'){
					++i;
					if(what.charAt(i)=='q'){
						++i;
						if(what.charAt(i)=='r'){
							++i;
							if(what.charAt(i)=='t'){
								++i;
								if(what.charAt(i)!='('){
									System.err.println("Using a monomial made up of sqrt Multipliers is not recommended, since this is a keyword to the Square Root calculation.");
									--i;
									--i;
									--i;
									--i;
									--i;
								}else{
									++Tree;
									ourLowerLevel = ourCurrentThing;
									ourCurrentThing = new Sqrt(ourLowerLevel);
									ourLowerLevel.addOperatorGroup(ourCurrentThing);
								}
							}else{
								--i;
							}
						}else{
							--i;
						}
					}else{
						--i;
					}
				}else{
					processMode = 2;
					ourCurrentThing.addMonomial(new NumberMonomial(what.charAt(i)));
				}
			}
			if (!Character.isDigit(what.charAt(i)) && processMode == 1){
				if (Character.isDigit(what.charAt(i))){
					ourCurrentThing.getLastMonomial().Number = connectNumbers(ourCurrentThing.getLastMonomial().Number,what.charAt(i));
				}
				if (Character.isAlphabetic(what.charAt(i))){
					processMode = 2;
					ourCurrentThing.getLastMonomial().addMultiplier(new Multiplier(ourCurrentThing.getLastMonomial()));
				}
				if (what.charAt(i) == '^'){
					processMode = 3;
					if (!Character.isDigit(what.charAt(i)) && what.charAt(i) != '-'){
						throw new IllegalArgumentException("Invalid power amount at index " + i + " in expression String " + what);
					}else{
						ourCurrentThing.getLastMonomial().pow = (int) (what.charAt(i));
					}
					
				}
			}
			if (what.charAt(i) == '^' && processMode == 2){
				processMode = 4;
				if (!Character.isDigit(what.charAt(i)) && what.charAt(i) != '-'){
					throw new IllegalArgumentException("Invalid power amount - you need to have a power of a number; not a monomial!");
				}else{
					ourCurrentThing.getLastMonomial().getLastMulti().pow = (int) (what.charAt(i));
				}
			}
			if(what.charAt(i) == '+'){
				processMode = 0; //reset
				ourCurrentThing.addOperator(new Operator(Operators.ADDITION));
			}
			if(what.charAt(i) == '-'){
				processMode = 0; //reset
				ourCurrentThing.addOperator(new Operator(Operators.SUBTRACTION));
			}
			if(what.charAt(i) == '*'){
				processMode = 0; //reset
				ourCurrentThing.addOperator(new Operator(Operators.MULTIPLICATION));
			}
			if(what.charAt(i) == '/'){
				processMode = 0; //reset
				ourCurrentThing.addOperator(new Operator(Operators.DIVISION));
			}
			if(what.charAt(i) == '%'){
				processMode = 0; //reset
				ourCurrentThing.addOperator(new Operator(Operators.MODULUS));
			}
			if (what.charAt(i) == '('){
				++Tree;
				ourLowerLevel = ourCurrentThing;
				ourCurrentThing = new OperatorGroup(ourLowerLevel);
				ourLowerLevel.addOperatorGroup(ourCurrentThing);
			}
			if (what.charAt(i) == ')'){
				if (Tree == 1){
					throw new SyntaxException("Invalid syntax - brackets were not open");
				}else{
				--Tree;
				ourLowerLevel = ourLowerLevel.lower;
				ourCurrentThing = ourLowerLevel;
				}
			}
			if (what.charAt(i) == '='){
				throw new SyntaxException("Invalid syntax - Expression requested to be solved is already solved");
			}
			if (what.charAt(i) == '>'){
				throw new SyntaxException("Invalid syntax - an Expression is treated as an inequality");
			}
			if (what.charAt(i) == '<'){
				throw new SyntaxException("Invalid syntax - an Expression is treated as an inequality");
			}
			if (what.charAt(i) == '~'){
				++i;
				if(what.charAt(i) == '='){
					throw new SyntaxException("Invalid syntax - Expression requested to be solved with an approximate result");
				}else{
					throw new SyntaxException("Invalid syntax - Illegal Character: " + what.charAt(i));
				}
			}
			++i;
		}
		if (Tree != 1){
			throw new SyntaxException("Invalid syntax - unclosed brackets");
		}
		if (ourCurrentThing.Group[ourCurrentThing.Group.length] instanceof Operator){
			throw new SyntaxException("Invalid syntax - you cant end an Expression with an operator");
		}
		return ourThing;
	}
	
	
	public static double connectNumbers(double initValue, int toConnect){
		return initValue * 10 + toConnect;
	}
	
	/**
	 * A complicated method made to handle mathematical operations between objects.
	 * @param ep
	 * @return
	 */
	public static double solveExpression(Expression ep){
		double result = 0.0;
		return result;
	}
}
