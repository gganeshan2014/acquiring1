package com.cg.sp.exceptionhandler;

public class InvalidDateRange extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidDateRange(String msg) {
        super(msg);
    }
}
