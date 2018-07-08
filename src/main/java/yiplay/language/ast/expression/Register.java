package yiplay.language.ast.expression;

import yiplay.language.ast.Expression;
import yiplay.language.visitor.Visitor;

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

	@Override
	public String toString() {
		return "R" + register;
	}
	
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Register))
			return false;
		
		Register temp = (Register)obj;
		return temp.getRegister() == register;
	}

	@Override
	public String translate() {
		String toTranslate = Integer.toBinaryString(register);
		
		while(toTranslate.length() < 3)
			toTranslate = "0" + toTranslate;
		
		return toTranslate;
	}
	
}
