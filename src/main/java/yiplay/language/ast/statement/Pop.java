package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Pop extends Statement{

	private Expression register;
	
	public Pop(int line, int column, Expression register) {
		super(line, column);
		this.register = register;
	}
	
	public Expression getRegister() {
		return register;
	}
	
	@Override
	public String toString() {
		return "Pop " + register;
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
		Pop other = (Pop) obj;
		if (register == null) {
			if (other.register != null)
				return false;
		} else if (!register.equals(other.register))
			return false;
		return true;
	}
	
}
