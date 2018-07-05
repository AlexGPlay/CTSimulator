package yiplay.language.ast;

import java.util.List;

import yiplay.language.visitor.Visitor;

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

	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Program))
			return false;
		
		Program temp = (Program)obj;
		
		if(temp.getStatements().size() != this.getStatements().size())
			return false;
		
		for(int i=0;i<statements.size();i++)
			if(!statements.get(i).equals(temp.getStatements().get(i)))
				return false;
		
		return true;
	}

}
