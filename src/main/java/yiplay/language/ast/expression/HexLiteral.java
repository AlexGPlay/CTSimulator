package yiplay.language.ast.expression;

import yiplay.language.ast.Expression;
import yiplay.language.visitor.Visitor;
import yiplay.util.Translate;

public class HexLiteral extends Expression{

	private String value;
	
	public HexLiteral(int line, int column, String number) {
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
		if(!(obj instanceof HexLiteral))
			return false;
		
		HexLiteral temp = (HexLiteral)obj;
		return temp.getValue().equals(this.getValue());
	}

	@Override
	public String translate() {
		String toTranslate;
		
		if(value.matches("0H.*"))
			toTranslate = value.substring(2);
		
		else if(value.matches(".*H"))
			toTranslate = value.substring(0, value.length()-1);
		
		else
			toTranslate = value;
		
		return Translate.toBinaryString(toTranslate, 16, 8);
	}
	
}
