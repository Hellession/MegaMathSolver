package net.hellession.megamathsolver.util;

import net.hellession.megamathsolver.logic.Logger;
import net.hellession.megamathsolver.math.NumberMonomial;

public class Util {

	public static String turnMultiplierstoString(NumberMonomial ofwhat){
		String result = "";
		for (int i=0;i<ofwhat.Multipliers.size();++i){
			Logger.logDebugInfo("Multiplier returns " + ofwhat.Multipliers.elementAt(i).toString());
			result = result + ofwhat.Multipliers.elementAt(i).toString();
		}
		Logger.logDebugInfo("Util class turned a NumberMonomial's Multipliers to a string, it is " + result);
		return result;
	}
}
