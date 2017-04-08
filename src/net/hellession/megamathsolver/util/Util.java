package net.hellession.megamathsolver.util;

import net.hellession.megamathsolver.math.NumberMonomial;

public class Util {

	public static String turnMultiplierstoString(NumberMonomial ofwhat){
		String result = "";
		for (int i=0;i<ofwhat.Multipliers.size();++i){
			result = result + ofwhat.Multipliers.elementAt(i).MultiChar;
		}
		return result;
	}
}
