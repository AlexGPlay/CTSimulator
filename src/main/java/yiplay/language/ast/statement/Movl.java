package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Movl extends Statement{

	private Expression register, number;
	
	public Movl(int line, int column, Expression register, Expression number) {
		super(line, column);
		this.register = register;
		this.number = number;
	}
	
	public Expression getRegister() {
		return register;
	}
	
	public Expression getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return "Movl " + register + ", " + number;
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
		Movl other = (Movl) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (register == null) {
			if (other.register != null)
				return false;
		} else if (!register.equals(other.register))
			return false;
		return true;
	}
	
}
