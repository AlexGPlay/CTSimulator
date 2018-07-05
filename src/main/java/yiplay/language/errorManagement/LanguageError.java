package yiplay.language.errorManagement;

public abstract class LanguageError {
	
	protected int line;
	protected int column;
	protected Object desc;
	
	public LanguageError(int line, int column, Object desc) {
		this.line = line;
		this.column = column;
		this.desc = desc;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public Object getDesc() {
		return desc;
	}
	
}
