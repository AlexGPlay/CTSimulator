package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Add extends Statement{

	private Expression rd, rs1, rs2;
	
	public Add(int line, int column, Expression rd, Expression rs1, Expression rs2) {
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

	@Override
	public String toString() {
		return "Add " + rd + ", " + rs1 + ", " + rs2;
	}
	
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Add))
			return false;
		
		Add temp = (Add)obj;
		
		if(!temp.getRd().equals(rd))
			return false;
		
		if(!temp.getRs1().equals(rs1))
			return false;
		
		return temp.getRs2().equals(rs2);
	}

}
