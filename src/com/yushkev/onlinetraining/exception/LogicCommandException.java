package com.yushkev.onlinetraining.exception;

public class LogicCommandException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LogicCommandException() {
		
	} 

    public LogicCommandException (String message) {
        super (message);
    }

    public LogicCommandException (Throwable cause) {
        super (cause);
    }

    public LogicCommandException (String message, Throwable cause) {
        super (message, cause);
    }


}
