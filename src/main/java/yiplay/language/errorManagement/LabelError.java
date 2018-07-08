package yiplay.language.errorManagement;

public class LabelError extends LanguageError{

	public LabelError(int line, int column, Object desc) {
		super(line, column, desc);
		this.errorType = "Label";
	}
	
}
