package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;

public class Mov extends Statement{

	private Expression rd, rs;
	private int movType;
	
	public Mov(int line, int column, Expression rd, Expression rs, int type) {
		super(line, column);
		this.rd = rd;
		this.rs = rs;
		this.movType = type;
	}
	
	public Expression getRd() {
		return rd;
	}
	
	public Expression getRs() {
		return rs;
	}
	
	public int getType() {
		return movType;
	}

}
