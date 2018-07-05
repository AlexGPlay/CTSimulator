package yiplay.language.ast.statement;

import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Ret extends Statement{

	public Ret(int line, int column) {
		super(line, column);
	}

	@Override
	public String toString() {
		return "Ret";
	}
	
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Ret))
			return false;
		
		return true;
	}
	
}
