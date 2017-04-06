package net.hellession.megamathsolver.exception;

public class SyntaxException extends Exception{
	public String message;
	
	public SyntaxException(String msg){
		this.message = msg;
	}
}
