package yiplay.language.ast.statement;

import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Nop extends Statement{

	public Nop(int line, int column) {
		super(line, column);
	}

	@Override
	public String toString() {
		return "Nop";
	}
	
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Nop))
			return false;
		
		return true;
	}

}
