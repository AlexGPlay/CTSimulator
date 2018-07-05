package yiplay.language.ast.statement;

import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Iret extends Statement{

	public Iret(int line, int column) {
		super(line, column);
	}

	@Override
	public String toString() {
		return "Iret";
	}
	
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Iret))
			return false;
		
		return true;
	}
	
}
