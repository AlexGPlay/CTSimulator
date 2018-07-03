package yiplay.language.ast.expression;

import yiplay.language.ast.Expression;

public class HexLiteral extends Expression{

	private String value;
	
	public HexLiteral(int line, int column, String number) {
		super(line, column);
		value = number;
	}

	public String getValue() {
		return value;
	}
	
}
