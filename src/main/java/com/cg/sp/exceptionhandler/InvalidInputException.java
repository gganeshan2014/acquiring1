package com.cg.sp.exceptionhandler;

public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidInputException(String value, String msg) {
        super(value + ", " + msg);
    }
}
