package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;

public class Int extends Statement{

	private Expression interruption;
	
	public Int(int line, int column, Expression interruption) {
		super(line, column);
		this.interruption = interruption;
	}
	
	public Expression getInterruption() {
		return interruption;
	}

}
