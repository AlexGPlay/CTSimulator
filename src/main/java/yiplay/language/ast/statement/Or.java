package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

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
	
	@Override
	public String toString() {
		return "Or " + rd + ", " + rs1 + ", " + rs2;
	}
	
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Or other = (Or) obj;
		if (rd == null) {
			if (other.rd != null)
				return false;
		} else if (!rd.equals(other.rd))
			return false;
		if (rs1 == null) {
			if (other.rs1 != null)
				return false;
		} else if (!rs1.equals(other.rs1))
			return false;
		if (rs2 == null) {
			if (other.rs2 != null)
				return false;
		} else if (!rs2.equals(other.rs2))
			return false;
		return true;
	}

}
