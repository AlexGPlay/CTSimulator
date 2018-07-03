package yiplay.language.ast.expression;

import yiplay.language.ast.Expression;

public class IntegerLiteral extends Expression{

	private int value;
	
	public IntegerLiteral(int line, int column, int number) {
		super(line, column);
		this.value = number;
	}
	
	public int getValue() {
		return value;
	}

}
