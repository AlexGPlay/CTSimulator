package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

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
	
	@Override
	public String toString() {
		String to;
		
		if(label != null)
			to = label;
		
		else
			to = lines.toString();
		
		return "Jmp " + to;
	}
	
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jmp other = (Jmp) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (lines == null) {
			if (other.lines != null)
				return false;
		} else if (!lines.equals(other.lines))
			return false;
		if (mode != other.mode)
			return false;
		return true;
	}
	
}
