package com.armenabrahamyan.languagelexing;

import java.util.Iterator;
import java.util.Map;

import com.armenabrahamyan.main.abstractparsetree.beans.Operator;
import com.armenabrahamyan.main.util.CustomInterpreterException;

/**
 * Responsible for creating lexical understanding of input code text. Defines
 * language/data structure
 * 
 * @author armenabrahamyan
 * 
 */
public class InterpreterLanguageLexing implements Iterator<String> {

	private Map<String, Operator> operators;
	private int pos = 0;
	private String input;
	private String previousToken;

	public InterpreterLanguageLexing(String input, Map<String, Operator> operators) {
		this.input = input;
		this.operators = operators;
	}

	@Override
	public boolean hasNext() {
		return (pos < input.length());
	}	

	@Override
	public String next() {
		StringBuilder token = new StringBuilder();
		if (pos >= input.length()) {
			return previousToken = null;
		}
		char ch = input.charAt(pos);
		while (Character.isWhitespace(ch) && pos < input.length()) {
			
			ch = input.charAt(++pos);
			
		}
		if (Character.isDigit(ch)) {
			
			while ((Character.isDigit(ch) || ch == '.')
					&& (pos < input.length())) {
				
				token.append(input.charAt(pos++));
				ch = pos == input.length() ? 0 : input.charAt(pos);
				
			}
		} else if (ch == '-'
				&& Character.isDigit(hitnextCharacter())
				&& ("(".equals(previousToken) || ",".equals(previousToken)
						|| previousToken == null || operators
							.containsKey(previousToken))) {
			
			token.append('-');
			pos++;
			token.append(next());
			
		} else if (Character.isLetter(ch)) {
			while ((Character.isLetter(ch) || Character.isDigit(ch) || (ch == '_'))
					&& (pos < input.length())) {
				
				token.append(input.charAt(pos++));
				ch = pos == input.length() ? 0 : input.charAt(pos);
				
			}
		} else if (ch == '(' || ch == ')' || ch == ',') {
			
			token.append(ch);
			pos++;
			
		} else {
			
			while (!Character.isLetter(ch) && !Character.isDigit(ch)
					&& !Character.isWhitespace(ch) && ch != '(' && ch != ')'
					&& ch != ',' && (pos < input.length())) {
				
				token.append(input.charAt(pos));
				pos++;
				ch = pos == input.length() ? 0 : input.charAt(pos);
				
				if (ch == '-') {
					break;
				}
				
			}
			if (!operators.containsKey(token.toString())) {
				
				throw new CustomInterpreterException(
						"Can not recognize input code !");
			}
		}
		
		return previousToken = token.toString();
	}
	
	private char hitnextCharacter() {
		if (pos < (input.length() - 1)) {
			return input.charAt(pos + 1);
		} else {
			return 0;
		}
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
