package yiplay.language.ast.statement;

import yiplay.language.ast.Statement;

public class Label extends Statement{

	private String label;
	
	public Label(int line, int column, String label) {
		super(line, column);
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

}
