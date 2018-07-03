package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;

public class Brs extends Statement{

	private Expression lines;
	private String label;
	
	public Brs(int line, int column, Expression lines) {
		super(line, column);
		this.lines = lines;
		label = null;
	}
	
	public Brs(int line, int column, String label) {
		super(line,column);
		this.label = label;
		lines = null;
	}
	
	public String getLabel() {
		return label;
	}
	
	public Expression getLines() {
		return lines;
	}

}
