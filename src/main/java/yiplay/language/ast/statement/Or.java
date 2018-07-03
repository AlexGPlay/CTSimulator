package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;

public class Or extends Statement{

	private Expression rd, rs1, rs2;
	
	public Or(int line, int column, Expression rd, Expression rs1, Expression rs2) {
		super(line, column);
		this.rd = rd;
		this.rs1 = rs1;
		this.rs2 = rs2;
	}
	
	public Expression getRd() {
		return rd;
	}
	
	public Expression getRs1() {
		return rs1;
	}
	
	public Expression getRs2() {
		return rs2;
	}

}
