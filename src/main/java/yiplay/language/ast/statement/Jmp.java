package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;

public class Jmp extends Statement{

	private int mode;
	
	private Expression lines;
	private String label;
	
	public Jmp(int line, int column, Expression lines, int mode) {
		super(line, column);
		this.lines = lines;
		this.mode = mode;
		label = null;
	}
	
	public Jmp(int line, int column, String label) {
		super(line,column);
		this.label = label;
		mode = 0;
		lines = null;
	}
	
	public String getLabel() {
		return label;
	}
	
	public Expression getLines() {
		return lines;
	}
	
	public int getMode() {
		return mode;
	}

}
