package yiplay.language.ast.statement;

import yiplay.language.ast.Expression;
import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Brnz extends Statement{

	private Expression lines;
	private String label;
	
	public Brnz(int line, int column, Expression lines) {
		super(line, column);
		this.lines = lines;
		label = null;
	}
	
	public Brnz(int line, int column, String label) {
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
	
	@Override
	public String toString() {
		String to;
		
		if(label != null)
			to = label;
		
		else
			to = lines.toString();

		return "Brnz " + to;
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
		Brnz other = (Brnz) obj;
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
