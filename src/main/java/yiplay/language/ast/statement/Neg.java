package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Neg extends Statement{

	private Expression rsd;
	
	public Neg(int line, int column, Expression rsd) {
		super(line, column);
		this.rsd = rsd;
	}
	
	public Expression getRsd() {
		return rsd;
	}
	
	@Override
	public String toString() {
		return "Neg " + rsd;
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
		Neg other = (Neg) obj;
		if (rsd == null) {
			if (other.rsd != null)
				return false;
		} else if (!rsd.equals(other.rsd))
			return false;
		return true;
	}

}
