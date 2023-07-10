package com.cg.sp.exceptionhandler;

public class NoDataFound extends RuntimeException {
    public NoDataFound(String message) {
        super(message);
    }
}
