package yiplay.language.ast;

import yiplay.language.visitor.Visitor;

public interface ASTNode {

	int getLine();
	int getColumn();
	
	Object accept(Visitor visitor, Object param);
	
}
