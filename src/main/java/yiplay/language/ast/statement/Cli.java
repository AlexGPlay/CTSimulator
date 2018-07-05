package yiplay.language.ast.statement;

import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Cli extends Statement{

	public Cli(int line, int column) {
		super(line, column);
	}

	@Override
	public String toString() {
		return "Cli";
	}
	
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Cli))
			return false;
		
		return true;
	}
	
}
