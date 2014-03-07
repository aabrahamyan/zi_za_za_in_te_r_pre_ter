package com.armenabrahamyan.main;

/**
 * Entry point class for providing code input and evaluating result
 * 
 * @author armenabrahamyan
 * 
 */
public class InterpreterEvaluator {

	public static void main(String[] args) {

		// NOTE: For calculating factorial, type : "0 FACTORIAL 5" where 5 is a
		// number of which factorial will be calculated
		ParseTreeExecutor parseTreeExecutor = new ParseTreeExecutor(
				" 0 FACTORIAL 5");
		System.out.println(parseTreeExecutor.execute());

	}

}
