package yiplay.language.ast.expression;

import yiplay.language.ast.Expression;
import yiplay.language.visitor.Visitor;

public class IntegerLiteral extends Expression{

	private int value;
	
	public IntegerLiteral(int line, int column, int number) {
		super(line, column);
		this.value = number;
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof IntegerLiteral))
			return false;
		
		IntegerLiteral temp = (IntegerLiteral)obj;
		return temp.getValue() == value;
	}

}
