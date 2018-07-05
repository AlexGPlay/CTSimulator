package yiplay.language.errorManagement;

public class LexicalError extends LanguageError{

	public LexicalError(int line, int column, Object desc) {
		super(line, column, desc);
	}

	@Override
	public String toString() {
		return String.format("Lexical error on line %s and column %s with description %s", line, column, desc);
	}
	
}
