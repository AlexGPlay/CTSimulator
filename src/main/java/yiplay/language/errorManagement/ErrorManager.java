package yiplay.language.errorManagement;

import java.util.ArrayList;
import java.util.List;

public class ErrorManager {

	public static final int LEXICAL = 0;
	public static final int SYNTACTIC = 1;
	
	public static ErrorManager manager;
	
	private List<LanguageError> errors;
	
	public static ErrorManager getManager() {
		if(manager == null)
			manager = new ErrorManager();
		return manager;
	}
	
	public ErrorManager() {
		errors = new ArrayList<LanguageError>();
	}
	
	public List<LanguageError> getErrors(){
		return errors;
	}
	
	public void addError(int type, int line, int column, Object desc) {
		switch(type) {
		case LEXICAL:
			errors.add(new LexicalError(line,column,desc));
			break;
		
		case SYNTACTIC:
			errors.add(new SyntacticError(line,column,desc));
			break;
		}
	}
	
	public void addError(LanguageError error) {
		errors.add(error);
	}
	
	public boolean hasErrors() {
		return errors.size() != 0;
	}
	
	public void printErrors() {
		for(LanguageError error : errors)
			System.err.println(error);
	}
	
}
