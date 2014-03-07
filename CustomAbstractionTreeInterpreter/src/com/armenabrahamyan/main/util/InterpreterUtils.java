package com.armenabrahamyan.main.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Not used now: A.A.
 * @author armenabrahamyan
 *
 */
public class InterpreterUtils {
	
	private static final String OPERATORS_COLLECTION = "[+\\-*\\/\\^%=(),]";
	private static final String DIGITS_COLLECTION = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";
	private static final String WHITESPACE = "\\s";
	
	/**
	 * Checks whether input is operator or not
	 * @param value
	 * @return
	 */
	public static boolean isOperator(final String value) {					
		return checkbyPattern(value);	
	}
	
	/**
	 * Checks if digit
	 * @param value
	 * @return
	 */
	public static boolean isDigit(final String value) {
		
		Pattern p = Pattern.compile(DIGITS_COLLECTION);		
		Matcher result = p.matcher(value);	
		
		return result.find();
	}
	
	/**
	 * Checks whether input contains white spaces
	 * @param value
	 * @return
	 */
	public static boolean isWhiteSpace(final String value) {
		
		Pattern p = Pattern.compile(WHITESPACE);		
		Matcher result = p.matcher(value);	
		
		return result.find();
		
	}
	
	/**
	 * Checks if an argument is a string identifier
	 * @param value
	 * @return
	 */
	public static boolean isIdentifier(final String value) {
		return (!isOperator(value) && !isWhiteSpace(value) && !isDigit(value));
	}
	
	
//---------------------- Privates ------------------//
	/**
	 * Checkes symbol existence
	 * @param value
	 * @return
	 */
	private static boolean checkbyPattern(final String value) {
				
		Pattern p = Pattern.compile(OPERATORS_COLLECTION);
		boolean hasSpecialChar = p.matcher(value).find();
		
		return hasSpecialChar;
	}
	
	
}
