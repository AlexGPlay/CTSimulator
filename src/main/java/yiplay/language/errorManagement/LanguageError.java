package yiplay.language.errorManagement;

public abstract class LanguageError {
	
	protected int line;
	protected int column;
	protected String errorType;
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
	
	public String getErrorType() {
		return errorType;
	}

	public Object getDesc() {
		return desc;
	}
	
	@Override
	public String toString() {
		return String.format("%s error on line %s and column %s with description %s", errorType, line, column, desc);
	}
	
}
