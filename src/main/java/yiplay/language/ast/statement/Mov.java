package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

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

	@Override
	public String toString() {
		if(movType == 0)
			return "Mov " + rd + ", " + rs;
		
		else if(movType == 1)
			return "Mov " + rd + ", [" + rs + "]";
		
		else if(movType == 2)
			return "Mov " + "[" + rd + "], " + rs;
		
		return null;
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
		Mov other = (Mov) obj;
		if (movType != other.movType)
			return false;
		if (rd == null) {
			if (other.rd != null)
				return false;
		} else if (!rd.equals(other.rd))
			return false;
		if (rs == null) {
			if (other.rs != null)
				return false;
		} else if (!rs.equals(other.rs))
			return false;
		return true;
	}
	
}
