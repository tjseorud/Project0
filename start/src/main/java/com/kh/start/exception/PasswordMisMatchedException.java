package com.kh.start.exception;

public class PasswordMisMatchedException extends RuntimeException {
	
	public PasswordMisMatchedException(String message) {
		super(message);
	}
}
