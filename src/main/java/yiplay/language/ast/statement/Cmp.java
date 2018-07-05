package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

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

	@Override
	public String toString() {
		return "Cmp " + rs1 + ", " + rs2;
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
		Cmp other = (Cmp) obj;
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
