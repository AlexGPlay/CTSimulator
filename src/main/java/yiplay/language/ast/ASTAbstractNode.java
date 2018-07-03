package yiplay.language.ast;

public abstract class ASTAbstractNode implements ASTNode{

	protected int line;
	protected int column;
	
	public ASTAbstractNode(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

}
