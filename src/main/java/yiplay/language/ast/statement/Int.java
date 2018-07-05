package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Int extends Statement{

	private Expression interruption;
	
	public Int(int line, int column, Expression interruption) {
		super(line, column);
		this.interruption = interruption;
	}
	
	public Expression getInterruption() {
		return interruption;
	}
	
	@Override
	public String toString() {
		return "Int " + interruption;
	}
	
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((interruption == null) ? 0 : interruption.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Int other = (Int) obj;
		if (interruption == null) {
			if (other.interruption != null)
				return false;
		} else if (!interruption.equals(other.interruption))
			return false;
		return true;
	}
	
}
