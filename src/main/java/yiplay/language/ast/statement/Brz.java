package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Brz extends Statement{

	private Expression lines;
	private String label;
	
	public Brz(int line, int column, Expression lines) {
		super(line, column);
		this.lines = lines;
		label = null;
	}
	
	public Brz(int line, int column, String label) {
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
	
	public void setLines(Expression lines) {
		this.lines = lines;
	}
	
	@Override
	public String toString() {
		String to;
		
		if(lines != null)
			to = lines.toString();
		
		else
			to = label;

		return "Brz " + to;
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
		Brz other = (Brz) obj;
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
		return true;
	}
	
}
