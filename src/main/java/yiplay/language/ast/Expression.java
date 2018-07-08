package yiplay.language.ast;

public abstract class Expression extends ASTAbstractNode{

	public Expression(int line, int column) {
		super(line, column);
	}
	
	public abstract String translate();

}
