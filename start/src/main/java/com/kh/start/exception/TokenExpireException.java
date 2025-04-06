package com.kh.start.exception;

public class TokenExpireException extends RuntimeException {
	
	public TokenExpireException(String message) {
		super(message);
	}
}
