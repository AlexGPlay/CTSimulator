package yiplay.language.ast.expression;

import yiplay.language.ast.Expression;

public class Register extends Expression{

	private int register;
	
	public Register(int line, int column, String register) {
		super(line, column);
		this.register = parseRegister(register);
	}
	
	private int parseRegister(String register) {
		String rOut = register.substring(1);
		return Integer.parseInt(rOut);
	}

	public int getRegister() {
		return register;
	}
	
}
