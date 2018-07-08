package yiplay.language.ast.expression;

import yiplay.language.ast.Expression;
import yiplay.language.visitor.Visitor;

public class BinaryLiteral extends Expression{

	private String value;
	
	public BinaryLiteral(int line, int column, String number) {
		super(line, column);
		value = number;
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof BinaryLiteral))
			return false;
		
		BinaryLiteral temp = (BinaryLiteral)obj;
		return temp.getValue().equals(this.getValue());
	}

	@Override
	public String translate() {
		String toTranslate = value;
		
		if(value.matches("0B.*"))
			toTranslate = value.substring(2);
		
		while(toTranslate.length()<8)
			toTranslate = "0" + toTranslate;
		
		return toTranslate;
	}

}
