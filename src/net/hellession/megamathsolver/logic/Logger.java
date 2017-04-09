package net.hellession.megamathsolver.logic;

public class Logger {

	public static void logDebugInfo(String msg){
		if (Values.debug == true){
			System.out.println("[INFO]" + msg);
		}
	}
	
	public static void logErrorInfo(String msg){
		if (Values.debug == true){
			System.err.println("[ERROR]" + msg);
		}
	}
	
	public static void logExceptionInfo(String msg, String ExceptionType){
		System.err.println("[" + ExceptionType + " Exception]" + msg);
	}
}
