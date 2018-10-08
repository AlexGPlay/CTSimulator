package yiplay;

import java.util.List;

import yiplay.language.Compiler;
import yiplay.language.errorManagement.ErrorManager;
import yiplay.tc.TeoricalComputer;
import yiplay.util.observer.GPRObserver;
import yiplay.util.observer.MemoryObserver;

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
	
	public int compileAndSave(String code) {
		Compiler compiler = new Compiler();
		List<String> instructions = compiler.compile(code);
		
		if(instructions == null) {
			ErrorManager.getManager().printErrors();
			return -1;
		}
		
		tc.loadInstructions(instructions);
		return 0;
	}
	
	public void runProgram() {
		tc.runProgram();
	}
	
	public void runInstruction() {
		tc.runInstruction();
	}
	
	public void registerMemoryObserver(MemoryObserver observer) {
		tc.registerMemoryObserver(observer);
	}
	
	public void registerGPRObserver(GPRObserver observer) {
		tc.registerGPRObserver(observer);
	}
	
}
