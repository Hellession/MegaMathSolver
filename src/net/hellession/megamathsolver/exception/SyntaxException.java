package net.hellession.megamathsolver.exception;

@SuppressWarnings("serial")
public class SyntaxException extends Exception{
	public String message;
	
	public SyntaxException(String msg){
		this.message = msg;
	}
}
