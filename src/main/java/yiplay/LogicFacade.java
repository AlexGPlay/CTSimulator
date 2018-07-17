package yiplay;

import java.util.List;

import yiplay.language.Compiler;
import yiplay.language.errorManagement.ErrorManager;
import yiplay.tc.TeoricalComputer;

public class LogicFacade {

	private static LogicFacade instance;
	private TeoricalComputer tc;
	
	private LogicFacade() {
		tc = new TeoricalComputer();
	}
	
	public static LogicFacade getInstance() {
		if(instance == null)
			instance = new LogicFacade();
		return instance;
	}
	
	public void compileAndSave(String code) {
		Compiler compiler = new Compiler();
		List<String> instructions = compiler.compile(code);
		
		if(instructions == null) {
			ErrorManager.getManager().printErrors();
			return;
		}
		
		tc.loadInstructions(instructions);
	}
	
	public void runProgram() {
		tc.runProgram();
	}
	
}
