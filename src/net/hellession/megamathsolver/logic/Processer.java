package net.hellession.megamathsolver.logic;

import java.util.Vector;

import net.hellession.megamathsolver.exception.SyntaxException;
import net.hellession.megamathsolver.math.*;

public class Processer {
	/**
	 * ?????
	 * @param what
	 * @return
	 */
	@Deprecated
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
		Logger.logDebugInfo("Parsing an expression...");
		int processMode = 0; // 0 - none, 1 - reading a Monomial(numbers)! 2 - reading a Monomial's multipliers! 3 - reading a Number's exponator, 4 - reading an exponator for a Multiplier,
		Expression ourThing = new Expression();
		/**
		 * The Tree var is responsible for showing how many "OperatorGroup" upwards have been made.
		 * This means that if there are brackets inside of an Expression, it will increment upon entering these brackets,
		 * then once again upon entering another brackets
		 */
		int Tree = 0;
		OperatorGroup ourCurrentThing = ourThing;
		OperatorGroup ourLowerLevel = null;
		what.toLowerCase();
		for(int i=0; i < what.length(); ++i){
			if (Character.isDigit(what.charAt(i)) && processMode == 0) { // A NumberMonomial with any int coefficient
				Logger.logDebugInfo("Stumbled accross a number");
				processMode = 1;
				ourCurrentThing.addMonomial(new NumberMonomial(what.charAt(i)));
				if(i+1 < what.length()){
				while(Character.isDigit(what.charAt(i+1)) == true){
					ourCurrentThing.getLastMonomial().Number = connectNumbers(ourCurrentThing.getLastMonomial().Number,what.charAt(i));
					++i;
					Logger.logDebugInfo("More numbers in a row found, final number = " + ourCurrentThing.getLastMonomial().Number);
				}}
			}
			if (Character.isAlphabetic(what.charAt(i)) && processMode == 0){ // A NumberMonomial with coefficient 1 and a Multiplier letter
				Logger.logDebugInfo("Stumbled accross a Monomial with a letter");
				if (ourCurrentThing.getLastMonomial().Multipliers.get(0).MultiChar == 's'){
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
									Logger.logDebugInfo("Detected a sqrt(), creating new OperatorGroup");
									++Tree;
									ourLowerLevel = ourCurrentThing;
									ourCurrentThing = new Sqrt(ourLowerLevel, Tree);
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
				Logger.logDebugInfo("Found multipliers for a Monomial with numbers, although it may be an Operator or something else than a Multiplier");
				if (Character.isDigit(what.charAt(i))){
					ourCurrentThing.getLastMonomial().Number = connectNumbers(ourCurrentThing.getLastMonomial().Number,what.charAt(i));
				}
				if (Character.isAlphabetic(what.charAt(i))){
					processMode = 2;
					ourCurrentThing.getLastMonomial().addMultiplier(new Multiplier(ourCurrentThing.getLastMonomial()));
				}
				if (what.charAt(i) == '^'){
					processMode = 3;
					++i; // UNFINISHED - THERE MIGHT BE MORE NUMBERS AFTER THE EXPONENTATION MARK
					if (!Character.isDigit(what.charAt(i)) && what.charAt(i) != '-'){
						throw new IllegalArgumentException("Invalid power amount at index " + i + " in expression String " + what);
					}else{
						if(what.charAt(i) == '-'){
							++i;
							int ok = - (int) what.charAt(i);
							ourCurrentThing.getLastMonomial().pow = ok;
						}else{
							ourCurrentThing.getLastMonomial().pow = (int) (what.charAt(i));
						}
					}
					
				}
			}
			if (what.charAt(i) == '^' && processMode == 2){
				Logger.logDebugInfo("Exponentation operator found");
				processMode = 4;
				++i; //UNFINISHED, 
				if (!Character.isDigit(what.charAt(i)) && what.charAt(i) != '-'){
					throw new IllegalArgumentException("Invalid power amount - you need to have a power of a number; not a monomial!");
				}else{
					if(what.charAt(i) == '-'){
						++i;
						int ok = - (int) what.charAt(i);
						ourCurrentThing.getLastMonomial().getLastMulti().pow = ok;
					}else{
						ourCurrentThing.getLastMonomial().getLastMulti().pow = (int) (what.charAt(i));
					}
				}
			}
			if(what.charAt(i) == '+'){
				Logger.logDebugInfo("Addition found");
				processMode = 0; //reset
				ourCurrentThing.addOperator(new Operator(Operators.ADDITION));
			}
			if(what.charAt(i) == '-'){
				Logger.logDebugInfo("Subtraction found");
				processMode = 0; //reset
				ourCurrentThing.addOperator(new Operator(Operators.SUBTRACTION));
			}
			if(what.charAt(i) == '*'){
				Logger.logDebugInfo("Multiplication found");
				processMode = 0; //reset
				ourCurrentThing.addOperator(new Operator(Operators.MULTIPLICATION));
			}
			if(what.charAt(i) == '/'){
				Logger.logDebugInfo("Division found");
				processMode = 0; //reset
				ourCurrentThing.addOperator(new Operator(Operators.DIVISION));
			}
			if(what.charAt(i) == '%'){
				Logger.logDebugInfo("Modulus found");
				processMode = 0; //reset
				ourCurrentThing.addOperator(new Operator(Operators.MODULUS));
			}
			if (what.charAt(i) == '('){
				Logger.logDebugInfo("OperatorGroup found, making a new OperatorGroup object");
				if (ourCurrentThing.Group.lastElement() instanceof NumberMonomial || ourCurrentThing.Group.lastElement() instanceof OperatorGroup){
					ourCurrentThing.Group.add(new Operator(Operators.MULTIPLICATION));
				}
				++Tree;
				ourLowerLevel = ourCurrentThing;
				ourCurrentThing = new OperatorGroup(ourLowerLevel, Tree);
				ourLowerLevel.addOperatorGroup(ourCurrentThing);
			}
			if (what.charAt(i) == ')'){
				Logger.logDebugInfo("OperatorGroup closing");
				if (Tree == 0){
					throw new SyntaxException("Invalid syntax - brackets were not open");
				}else{
				--Tree;
				ourCurrentThing = ourLowerLevel;
				ourLowerLevel = ourLowerLevel.lower;
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
		}
		if (Tree != 0){
			throw new SyntaxException("Invalid syntax - unclosed brackets");
		}
		if (ourCurrentThing.Group.lastElement() instanceof Operator){
			throw new SyntaxException("Invalid syntax - you cant end an Expression with an operator");
		}
		Logger.logDebugInfo("Parsing finished");
		return ourThing;
	}
	
	
	public static double connectNumbers(double initValue, int toConnect){
		return initValue * 10 + toConnect;
	}
	
	/**
	 * A complicated method made to handle mathematical operations between objects.
	 * @param ep - an Expression to solve.
	 * @return the result.
	 * @throws SyntaxException 
	 */
	public static Result solveExpression(Expression ep) throws SyntaxException{
		Logger.logDebugInfo("Solving expression");
		Result result = null;
		Vector<OperatorGroup> Opgrouplist = repeatableOpgrSearch(ep); //list that will be taken out
		while(Opgrouplist.size()>1){
			Logger.logDebugInfo("Calculating highest OperatorGroup");
			OperatorGroup FH = findFirstHighest(Opgrouplist,ep);
			Result toPlace = solveGroup(FH);
			if (toPlace.type == 1){
				int ind = FH.lower.Group.indexOf(FH);
				FH.lower.Group.remove(ind);
				FH.lower.Group.insertElementAt(toPlace.resultMonomial, ind);
				Opgrouplist.remove(FH);
			}
		}
		result = solveGroup(Opgrouplist.elementAt(0));
		Logger.logDebugInfo("Expression solved");
		return result;
	}
	
	@Deprecated
	public static int hasOpGroup(OperatorGroup where){
		int OpGroupAmount = 0;
		for(int i=0;where.Group.size()>i;++i){
			if (where.Group.elementAt(i)instanceof OperatorGroup){
				//isThereOpGroup=(OperatorGroup) ep.Group.elementAt(i);
				++OpGroupAmount;
			}
		}
		return OpGroupAmount;
	}
	
	/**
	 * Will return a Vector full of OperatorGroups ONLY inside of the expression passed as an argument.
	 * @param ep - where to get OperatorGroup objects from.
	 * @return a Vector full of OperatorGroup objects
	 */
	public static Vector<OperatorGroup> repeatableOpgrSearch(Expression ep){
		Vector<OperatorGroup> PureOpGroups = new Vector<OperatorGroup>();
		PureOpGroups.add(ep);
		findAllOpgr(PureOpGroups, ep);
		return PureOpGroups;
	}
	
	public static void findAllOpgr(Vector<OperatorGroup> opgroups, OperatorGroup opg){
		for (int i=0;i<opg.Group.size();++i){
			if (opg.Group.elementAt(i) instanceof OperatorGroup){
				opgroups.addElement((OperatorGroup) opg.Group.elementAt(i)); 
				findAllOpgr(opgroups, (OperatorGroup) opg.Group.elementAt(i));
			}
		}
	}
	
	/**
	 * Used to find the first occurence of the highest level of an OperatorGroup, meaning it does not have any other OperatorGroups inside of it
	 * @param opgroups - must be processed by repeatableOpgrSearch(Expression) first!
	 * @param exp - the initial Expression
	 * @return
	 */
	public static OperatorGroup findFirstHighest(Vector<OperatorGroup> opgroups, Expression exp){
		OperatorGroup toReturn = null;
		int recordLevel = 0;
		OperatorGroup recordHolder = exp;
		for (int p=0;p<opgroups.size();++p){
			if (opgroups.elementAt(p).level > recordLevel){
				recordLevel = opgroups.elementAt(p).level;
				recordHolder = opgroups.elementAt(p);
				Logger.logDebugInfo("New record holder found at level " + recordLevel);
			}
		}
		toReturn = recordHolder;
		return toReturn;
	}
	
	
	/**
	 * This method should only be called, if the argument passed does NOT have any nested other OperatorGroups and only simple operations, like addition, subtraction,
	 * multiplication, division or modulus.
	 * @param group - the OperatorGroup, that does NOT contain other OperatorGroups within itself.
	 * @return the result.
	 * @throws SyntaxException 
	 */
	public static Result solveGroup(OperatorGroup gr) throws SyntaxException{
		Logger.logDebugInfo("Method to solve simple OperatorGroup called");
		Result result = null;
		//step 1 - find ALL multiplication or division possible steps and complete them
		for(int i=0;i<gr.Group.size();++i){
			if (gr.Group.elementAt(i)instanceof Operator){
				// this means elements at indexes i+1 and i-1 are 100% NumberMonomials
				Operator weWorkWith = (Operator) gr.Group.elementAt(i); // done not to raise Exceptions!
				if ( weWorkWith.op == Operators.MULTIPLICATION){
					Logger.logDebugInfo("Multiplying");
					NumberMonomial rightResult = (NumberMonomial) gr.Group.elementAt(i+1);
					NumberMonomial leftSide = (NumberMonomial) gr.Group.elementAt(i-1);
					rightResult.Number = leftSide.Number * rightResult.Number;
					
					if (leftSide.Multipliers.size() > 0){
						for(int p=0;p<leftSide.Multipliers.size();++p){
							if (findSameMultiplier(leftSide.Multipliers.elementAt(p).MultiChar, rightResult) > -1){ // SHOULD BE CHANGED BECAUSE WE CAN'T KNOW FOR SURE THAT SAME MULTIPLIERS WOULD HAVE THE SAME INDEX
								++rightResult.Multipliers.elementAt(findSameMultiplier(leftSide.Multipliers.elementAt(p).MultiChar, rightResult)).pow;
							}else{
							rightResult.Multipliers.addElement(leftSide.Multipliers.elementAt(p));
							}
						}
					}
					
					gr.Group.removeElementAt(i);
					gr.Group.removeElementAt(i-1);
				}
				if ( weWorkWith.op == Operators.DIVISION){
					Logger.logDebugInfo("Dividing");
					NumberMonomial rightResult = (NumberMonomial) gr.Group.elementAt(i+1);
					NumberMonomial leftSide = (NumberMonomial) gr.Group.elementAt(i-1);
					rightResult.Number = leftSide.Number / rightResult.Number;
					
					if (rightResult.Multipliers.size() > 0){
						for(int p=0;p<rightResult.Multipliers.size();++p){ // dont use 'contains()' method of Vector, because it checks for EXACT object, we don't have that
							if (findSameMultiplier(rightResult.Multipliers.elementAt(p).MultiChar, leftSide) > -1){
								rightResult.Multipliers.remove(p);
								--p;
							}else{
								throw new SyntaxException("Invalid Syntax - The operation " + leftSide.Number + leftSide.Multipliers.toString() + " + " + rightResult.Number + rightResult.Multipliers.toString() + " rationalizes the expression, making it illegal");
							}
						}
					}
					
					gr.Group.removeElementAt(i);
					gr.Group.removeElementAt(i-1);
				}
			}
		}
		
		//step 2 - find ALL addition and subtraction possible steps and complete them
		for (int i=0;i<gr.Group.size();++i){
			if (gr.Group.elementAt(i) instanceof Operator){
				Operator weWorkWith = (Operator) gr.Group.elementAt(i);
				NumberMonomial leftSide = (NumberMonomial) gr.Group.elementAt(i-1);
				NumberMonomial rightResult = (NumberMonomial) gr.Group.elementAt(i+1);
				if (weWorkWith.op == Operators.ADDITION && checkIfEqualMulti(leftSide.Multipliers, rightResult)){
					Logger.logDebugInfo("Adding");
					rightResult.Number = leftSide.Number + rightResult.Number;
					gr.Group.removeElementAt(i);
					gr.Group.removeElementAt(i-1);
				}
				if (weWorkWith.op == Operators.SUBTRACTION && checkIfEqualMulti(leftSide.Multipliers, rightResult)){
					Logger.logDebugInfo("Subtracting");
					rightResult.Number = leftSide.Number - rightResult.Number;
					gr.Group.removeElementAt(i);
					gr.Group.removeElementAt(i-1);
				}
			}
		}
		if (gr.Group.size() == 1 && gr.Group.elementAt(0) instanceof NumberMonomial){
			result = new Result(1, (NumberMonomial) gr.Group.elementAt(0));
			Logger.logDebugInfo("Simple result made of only 1 NumberMonomial, the result is " + result.toString());
		}
		Logger.logDebugInfo("Simple OperatorGroup of an Expression solved");
		return result;
	}
	
	/**
	 * Will find the first occurence of the Multiplier, that has the same character as the char passed as toMatch argument, then will return its index in Multipl's
	 * Multipliers list or -1 as a result, if none of these Multipliers were found.
	 * @param toMatch - which char should be found in Multipl.Multipliers
	 * @param Multipl - where to look for the matching char's multiplier.
	 * @return index of the character inside of Multipl's Multipliers Vector, if none was found, then -1.
	 */
	public static int findSameMultiplier(char toMatch, NumberMonomial Multipl){
		int correctIndex = -1;
		for (int index=0;index<Multipl.Multipliers.size();++index){
			if(toMatch == Multipl.Multipliers.elementAt(index).MultiChar){
				correctIndex = index;
			}
		}
		return correctIndex;
	}
	
	public static boolean checkIfEqualMulti(Vector<Multiplier> listOne, NumberMonomial listTwo){
		boolean success = true;
		for (int y=0;y<listOne.size();++y){
			if (findSameMultiplier(listOne.get(y).MultiChar, listTwo) > -1){
				// yay
			}else{
				success = false;
			}
		}
		return success;
	}
}
