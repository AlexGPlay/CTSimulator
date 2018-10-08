package yiplay.tc;

import java.util.ArrayList;
import java.util.List;

import yiplay.tc.cpu.ControlUnit;
import yiplay.tc.cpu.bus.InternalBus;
import yiplay.tc.cpu.register.GeneralPurpousesRegisters;
import yiplay.tc.cpu.register.ProgramCounter;
import yiplay.tc.cpu.register.StatusRegister;
import yiplay.tc.memory.Memory;
import yiplay.util.Translate;
import yiplay.util.observer.GPRObserver;
import yiplay.util.observer.IBObserver;
import yiplay.util.observer.MemoryObserver;

public class TeoricalComputer {

	public void loadInstructions(List<String> instructions) {
		List<Short> cInstructions = new ArrayList<Short>();
		
		for(String s : instructions) {
			short temp = (short) Translate.toDecimalInteger(s);
			cInstructions.add(temp);
		}
		
		((Memory)Memory.getInstance()).saveInstructions(0, cInstructions);
		resetTC();
	}
	
	private void resetTC() {
		((ProgramCounter)ProgramCounter.getInstance()).setData((short) 0);
		((StatusRegister)StatusRegister.getInstance()).setData((short) 0);
		((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).resetRegisters();
		((ControlUnit)ControlUnit.getInstance()).Fin();
	}
	
	public void runProgram() {
		((ControlUnit)ControlUnit.getInstance()).runAll();
	}
	
	public void runInstruction() {
		((ControlUnit)ControlUnit.getInstance()).runInstruction();
	}
	
	public void registerMemoryObserver(MemoryObserver observer) {
		((Memory)Memory.getInstance()).addObserver(observer);
	}
	
	public void registerGPRObserver(GPRObserver observer) {
		((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).addObserver(observer);
	}
	
	public void registerIBObserver(IBObserver observer) {
		((InternalBus)InternalBus.getInstance()).addObserver(observer);
	}
	
}
