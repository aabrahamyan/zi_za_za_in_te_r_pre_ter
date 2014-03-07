package com.armenabrahamyan.main.util;

/**
 * Custom exception, in order not to throw global "Exception"
 * @author armenabrahamyan
 *
 */
@SuppressWarnings("serial")
public class CustomInterpreterException extends RuntimeException {

	public CustomInterpreterException(String message) {
		super(message);
	}
}
