zi_za_za_in_te_r_pre_ter
========================

Usage example:

ParseTreeExecutor parseTreeExecutor = new ParseTreeExecutor("(2+3) * 4");


System.out.println(parseTreeExecutor.execute());


Output: 20

========================

ParseTreeExecutor parseTreeExecutor = new ParseTreeExecutor(" 0 FACTORIAL 5");
System.out.println(parseTreeExecutor.execute());

Output: Factorial of 5 is 120

========================

This custom interpreter uses "Shunting-yard algorithm" in order to produce Reverse Polish Notation. Calculates following experssions:

"+", "-", ">", "<", ">=","<=", "FACTORIAL", "%", "/", "*".

An architecture provides an easy way to add new expressions and operators.




