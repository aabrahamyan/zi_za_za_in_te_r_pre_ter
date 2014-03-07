package com.armenabrahamyan.main.abstractparsetree.beans;


/**
 * Operator class definition (similar to bean, though abstract because of execute method)
 * @author armenabrahamyan
 *
 */
public abstract class Operator {
	
	private String operatorName;
	
	// This one is a priority
	private int precedence;
	
	// Notes whether it is left power binded or not
	private boolean leftBinded;

	/**
	 * Create a new operator with priority and power binding
	 * @param oper
	 * @param precedence
	 * @param leftBinded
	 */
	public Operator(final String operatorName, int precedence, boolean leftBinded) {
		this.operatorName = operatorName;
		this.precedence = precedence;
		this.leftBinded = leftBinded;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public int getPrecedence() {
		return precedence;
	}

	public boolean isLeftBinded() {
		return leftBinded;
	}

	
	public abstract String execute(final Integer a, final Integer b);
}
