package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;

public class Push extends Statement{

	private Expression register;
	
	public Push(int line, int column, Expression register) {
		super(line, column);
		this.register = register;
	}
	
	public Expression getRegister() {
		return register;
	}

}
