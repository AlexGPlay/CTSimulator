package yiplay.language.errorManagement;

public class LengthError extends LanguageError{

	public LengthError(int line, int column, Object desc) {
		super(line, column, desc);
		this.errorType = "Length";
	}

}