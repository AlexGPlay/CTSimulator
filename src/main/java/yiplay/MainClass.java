package yiplay;

import yiplay.language.Compiler;
import yiplay.language.errorManagement.ErrorManager;

public class MainClass {

	public static void main(String args[]) {
		Compiler compiler = new Compiler();
		compiler.compile("mov1234");
		
		if(ErrorManager.getManager().hasErrors())
			ErrorManager.getManager().printErrors();
		
	}
}