package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;

public class Cmp extends Statement{

	private Expression rs1, rs2;
	
	public Cmp(int line, int column, Expression rs1, Expression rs2) {
		super(line, column);
		this.rs1 = rs1;
		this.rs2 = rs2;
	}
	
	public Expression getRs1() {
		return rs1;
	}
	
	public Expression getRs2() {
		return rs2;
	}

}
