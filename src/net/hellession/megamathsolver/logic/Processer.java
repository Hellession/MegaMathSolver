package net.hellession.megamathsolver.logic;

import net.hellession.megamathsolver.math.*;

public abstract class Processer {
	public static String process(String what){
		return "";
	}
	
	@SuppressWarnings("unused")
	public static void parseMathematically(String what){
		int processMode = 0; // 0 - none, 1 - reading a Monomial(numbers)! 2 - reading a Monomial's multipliers! 3 - reading a Number's multiplier, 4 - reading a Multiplier's multiplier
		boolean readingAGroup = false;
		Expression ourThing = new Expression();
		OperatorGroup[] GroupList = {}; // DEPRECATED - DON'T USE! Getting deleted soon.
		NumberMonomial[] MonomialList = {}; // list of Monomials from newest made to last made.    DEPRECATED - DON'T USE! Getting deleted soon.
		what.toLowerCase();
		for(int i=0; i < what.length() + 1; ++i){
			if (Character.isDigit(what.charAt(i)) && processMode == 0) { // A NumberMonomial with any int coefficient
				processMode = 1;
				ourThing.addMonomial(new NumberMonomial(what.charAt(i)));
				while(Character.isDigit(what.charAt(i+1)) == true){
					ourThing.getLastMonomial().Number = connectNumbers(ourThing.getLastMonomial().Number,what.charAt(i));
					++i;
				}
			}
			if (Character.isAlphabetic(what.charAt(i)) && processMode == 0){ // A NumberMonomial with coefficient 1 and a Multiplier letter
				
			}
			if (!Character.isDigit(what.charAt(i)) && processMode == 1){
				if (Character.isDigit(what.charAt(i))){
					ourThing.getLastMonomial().Number = connectNumbers(ourThing.getLastMonomial().Number,what.charAt(i));
				}
				if (Character.isAlphabetic(what.charAt(i))){
					processMode = 2;
					ourThing.getLastMonomial().addMultiplier(new Multiplier(ourThing.getLastMonomial()));
				}
				if (what.charAt(i) == '^'){
					processMode = 3;
					if (!Character.isDigit(what.charAt(i)) && what.charAt(i) != '-'){
						throw new IllegalArgumentException("Invalid power amount at index " + i + " in expression String " + what);
					}else{
						ourThing.getLastMonomial().pow = (int) (what.charAt(i));
					}
					
				}
			}
			if (what.charAt(i) == '^' && processMode == 2){
				processMode = 4;
				if (!Character.isDigit(what.charAt(i)) && what.charAt(i) != '-'){
					throw new IllegalArgumentException("Invalid power amount - you need to have a power of a number; not a monomial!");
				}else{
					ourThing.getLastMonomial().getLastMulti().pow = (int) (what.charAt(i));
				}
			}
			if(what.charAt(i) == '+'){
				processMode = 0; //reset
				ourThing.addOperator(new Operator()) // PUT OPERATOR CLASS IN ITS OWN PUBLIC FILE OK?
			}
			
			++i;
		}
	}
	
	public static double connectNumbers(double initValue, int toConnect){
		return initValue * 10 + toConnect;
	}
}
