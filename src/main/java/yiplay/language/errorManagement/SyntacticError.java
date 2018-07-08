package yiplay.language.errorManagement;

public class SyntacticError extends LanguageError{

	public SyntacticError(int line, int column, Object desc) {
		super(line, column, desc);
		this.errorType = "Syntactic";
	}

}
