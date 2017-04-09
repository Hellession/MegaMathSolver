package net.hellession.megamathsolver.exception;

import net.hellession.megamathsolver.logic.Logger;

@SuppressWarnings("serial")
public class SyntaxException extends Exception{
	public String message;
	
	public SyntaxException(String msg){
		this.message = msg;
		Logger.logExceptionInfo(msg, "Syntax");
	}
}
