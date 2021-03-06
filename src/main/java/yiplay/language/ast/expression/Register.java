package yiplay.language.ast.expression;

import yiplay.language.ast.Expression;
import yiplay.language.visitor.Visitor;
import yiplay.util.Translate;

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
		String toTranslate = Translate.toBinaryString((short) register,3);
		
		return toTranslate;
	}
	
}
