package yiplay.language.ast.statement;

import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Sti extends Statement{

	public Sti(int line, int column) {
		super(line, column);
	}

	@Override
	public String toString() {
		return "Sti";
	}
	
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Sti))
			return false;
		
		return true;
	}

}
