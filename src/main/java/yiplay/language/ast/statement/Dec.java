package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;

public class Dec extends Statement{

	private Expression rsd;
	
	public Dec(int line, int column, Expression rsd) {
		super(line, column);
		this.rsd = rsd;
	}
	
	public Expression getRsd() {
		return rsd;
	}

}
