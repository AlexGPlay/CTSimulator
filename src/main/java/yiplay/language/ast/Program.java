package yiplay.language.ast;

import java.util.List;

public class Program extends ASTAbstractNode{

	private List<Statement> statements;
	
	public Program(int line, int column, List<Statement> statements) {
		super(line, column);
		this.statements = statements;
	}
	
	public List<Statement> getStatements() {
		return statements;
	}
	
	@Override
	public String toString() {
		String text = "";
		
		for(Statement statement : statements)
			text += statement + "\n";
		
		return text;
	}

}
