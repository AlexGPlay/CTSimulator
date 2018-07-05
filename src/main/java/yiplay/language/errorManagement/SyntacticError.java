package yiplay.language.errorManagement;

public class SyntacticError extends LanguageError{

	public SyntacticError(int line, int column, Object desc) {
		super(line, column, desc);
	}
	
	@Override
	public String toString() {
		return String.format("Syntactic error on line %s and column %s with description %s", line, column, desc);
	}

}
