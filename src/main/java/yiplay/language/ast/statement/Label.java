package yiplay.language.ast.statement;

import yiplay.language.ast.Statement;
import yiplay.language.visitor.Visitor;

public class Label extends Statement{

	private String label;
	
	public Label(int line, int column, String label) {
		super(line, column);
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return label + ":";
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
		Label other = (Label) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	

}
