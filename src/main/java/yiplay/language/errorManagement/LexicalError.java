package yiplay.language.errorManagement;

public class LexicalError extends LanguageError{

	public LexicalError(int line, int column, Object desc) {
		super(line, column, desc);
		this.errorType = "Lexical";
	}
	
}
