package com.cg.sp.exceptionhandler;

public class InvalidSubscription extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public InvalidSubscription(String message) {
        super(message);
    }
}
