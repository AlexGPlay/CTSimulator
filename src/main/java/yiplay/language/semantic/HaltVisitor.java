package yiplay.language.semantic;

import yiplay.language.ast.Program;
import yiplay.language.ast.Statement;
import yiplay.language.ast.statement.Halt;
import yiplay.language.visitor.AbstractVisitor;

public class HaltVisitor extends AbstractVisitor{

	public Object visit(Program ast, Object param) {
		int haltCount = 0;
		
		for(Statement statement : ast.getStatements()) {
			if(statement instanceof Halt)
				haltCount++;
		}
		
		if(haltCount == 0)
			ast.getStatements().add(new Halt(ast.getStatements().size(),0));
		
		return null;
	}
	
}
