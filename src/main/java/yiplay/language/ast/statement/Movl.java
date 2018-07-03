package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;

public class Movl extends Statement{

	private Expression register, number;
	
	public Movl(int line, int column, Expression register, Expression number) {
		super(line, column);
		this.register = register;
		this.number = number;
	}
	
	public Expression getRegister() {
		return register;
	}
	
	public Expression getNumber() {
		return number;
	}

}
