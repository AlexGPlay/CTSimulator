package yiplay.language.ast.statement;

import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Halt extends Statement{

	public Halt(int line, int column) {
		super(line, column);
	}

	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	@Override
	public String toString() {
		return "Halt";
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Halt))
			return false;
		
		return true;
	}

}
