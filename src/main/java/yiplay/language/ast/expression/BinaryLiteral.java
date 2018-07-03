package yiplay.language.ast.expression;

import yiplay.language.ast.Expression;

public class BinaryLiteral extends Expression{

	private String value;
	
	public BinaryLiteral(int line, int column, String number) {
		super(line, column);
		value = number;
	}
	
	public String getValue() {
		return value;
	}

}
