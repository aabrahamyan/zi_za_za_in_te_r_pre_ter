package com.armenabrahamyan.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.armenabrahamyan.languagelexing.InterpreterLanguageLexing;
import com.armenabrahamyan.main.abstractparsetree.beans.Operator;
import com.armenabrahamyan.main.util.CustomInterpreterException;

/**
 * 
 * @author armenabrahamyan
 * 
 */
public class ParseTreeExecutor {

	public Map<String, String> variables = new HashMap<String, String>();
	private String inputeCode = null;
	private List<String> reversePolishNotation = null;
	private Map<String, Operator> operators = new HashMap<String, Operator>();

	public ParseTreeExecutor(String expression) {
		this.inputeCode = expression;
		// Settings operators list, that must be lexically understood
		addNewSymbols();
	}

	/**
	 * Assingin to each operator it's right power binding and left power
	 * binding, also priority We have to define operators via 'inflix' methods
	 */
	private void addNewSymbols() {
		addSymbol(new Operator("+", 20, true) {
			@Override
			public String execute(final Integer a, final Integer b) {
				return String.valueOf(a + b);
			}
		});

		addSymbol(new Operator("-", 20, true) {
			@Override
			public String execute(final Integer a, final Integer b) {
				return String.valueOf(b - a);
			}
		});

		addSymbol(new Operator("*", 30, true) {
			@Override
			public String execute(final Integer a, final Integer b) {
				return String.valueOf(a * b);
			}
		});

		addSymbol(new Operator("/", 30, true) {
			@Override
			public String execute(final Integer a, final Integer b) {
				return String.valueOf(b / a);
			}
		});

		addSymbol(new Operator("%", 30, true) {
			@Override
			public String execute(final Integer a, final Integer b) {
				return String.valueOf(b % a);
			}
		});

		addSymbol(new Operator(">", 10, false) {
			@Override
			public String execute(final Integer a, final Integer b) {
				return (b > a) ? String.valueOf(1) : String.valueOf(0);
			}
		});

		addSymbol(new Operator(">=", 10, false) {
			@Override
			public String execute(final Integer a, final Integer b) {
				return (b >= a) ? String.valueOf(1) : String.valueOf(0);
			}
		});

		addSymbol(new Operator("<", 10, false) {
			@Override
			public String execute(final Integer a, final Integer b) {
				return (b < a) ? String.valueOf(1) : String.valueOf(0);
			}
		});

		addSymbol(new Operator("<=", 10, false) {
			@Override
			public String execute(final Integer a, final Integer b) {
				return (b <= a) ? String.valueOf(1) : String.valueOf(0);
			}
		});

		addSymbol(new Operator("==", 7, false) {
			@Override
			public String execute(final Integer a, final Integer b) {
				return (a == b) ? String.valueOf(1) : String.valueOf(0);
			}
		});

		addSymbol(new Operator("!=", 7, false) {
			@Override
			public String execute(final Integer a, final Integer b) {
				return (a != b) ? String.valueOf(1) : String.valueOf(0);
			}
		});

		addSymbol(new Operator("FACTORIAL", 7, false) {
			@Override
			public String execute(final Integer a, final Integer b) {
				if (a > 0) {
					int result = 1;
					for (int i = 1; i <= a; i++) {
						result *= i;
					}

					return String.valueOf(result);
				}

				return "Factorial must be calculated for unsigned number";
			}
		});
	}

	/**
	 * Checks if character from inputeCode a number
	 * 
	 * @param st
	 * @return
	 */
	private boolean isNumber(final String characterString) {
		if (characterString.charAt(0) == '-' && characterString.length() == 1)
			return false;
		for (char character : characterString.toCharArray()) {
			if (!Character.isDigit(character) && character != '-'
					&& character != '.')
				return false;
		}
		return true;
	}

	/**
	 * Creates abstract parse tree
	 * 
	 * @param expression
	 * @return
	 */
	private List<String> parse(String expression) {
		List<String> result = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();

		InterpreterLanguageLexing tokenizer = new InterpreterLanguageLexing(
				expression, operators);

		while (tokenizer.hasNext()) {
			String token = tokenizer.next();
			if (isNumber(token)) {

				result.add(token);

			} else if (variables.containsKey(token)) {

				result.add(variables.get(token));

			} else if (Character.isLetter(token.charAt(0))) {

				stack.push(token);

			} else if (",".equals(token)) {

				while (!stack.isEmpty() && !"(".equals(stack.peek())) {

					result.add(stack.pop());

				}
				if (stack.isEmpty()) {

					throw new CustomInterpreterException(
							"Invalid code input error !");
				}

			} else if (operators.containsKey(token)) {

				final Operator firstOperator = operators.get(token);
				String secondToken = stack.isEmpty() ? null : stack.peek();
				while (operators.containsKey(secondToken)
						&& ((firstOperator.isLeftAssoc() && firstOperator
								.getPrecedence() <= operators.get(secondToken)
								.getPrecedence()) || (firstOperator
								.getPrecedence() < operators.get(secondToken)
								.getPrecedence()))) {
					result.add(stack.pop());
					secondToken = stack.isEmpty() ? null : stack.peek();
				}
				stack.push(token);
			} else if ("(".equals(token)) {

				stack.push(token);

			} else if (")".equals(token)) {

				while (!stack.isEmpty() && !"(".equals(stack.peek())) {
					result.add(stack.pop());
				}

				if (stack.isEmpty()) {
					throw new RuntimeException("Expected closing parenthesis");
				}
				stack.pop();

			}
		}
		while (!stack.isEmpty()) {

			String element = stack.pop();

			if ("(".equals(element) || ")".equals(element)) {

				throw new RuntimeException("Expected closing parenthesis");

			}
			if (!operators.containsKey(element)) {

				throw new RuntimeException("Unknown operator or function: "
						+ element);

			}

			result.add(element);
		}
		return result;
	}

	/**
	 * Evaluates the input code, by pushing and poping from stack. Per Philipp's
	 * requirements
	 */
	public String execute() {

		final Stack<String> stack = new Stack<String>();

		for (String token : toListString()) {
			if (operators.containsKey(token)) {
				final String leftVar = stack.pop();
				final String rightVar = stack.pop();
				stack.push(operators.get(token).execute(
						Integer.parseInt(leftVar), Integer.parseInt(rightVar)));
			} else {
				stack.push(new String(token));
			}
		}
		return stack.pop();
	}

	/**
	 * 
	 * @param operator
	 * @return
	 */
	public Operator addSymbol(final Operator operator) {
		return operators.put(operator.getOperatorName(), operator);
	}

	/**
	 * Returns an abstract syntax parse tree
	 * 
	 * @return
	 */
	private List<String> toListString() {
		if (reversePolishNotation == null) {
			reversePolishNotation = parse(this.inputeCode);
		}
		return reversePolishNotation;
	}

}
