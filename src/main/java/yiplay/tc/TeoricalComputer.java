package yiplay.tc;

import java.util.ArrayList;
import java.util.List;

import yiplay.tc.cpu.ControlUnit;
import yiplay.tc.memory.Memory;
import yiplay.util.Translate;

public class TeoricalComputer {

	public void loadInstructions(List<String> instructions) {
		List<Short> cInstructions = new ArrayList<Short>();
		
		for(String s : instructions) {
			short temp = (short) Translate.toDecimalInteger(s);
			cInstructions.add(temp);
		}
		
		((Memory)Memory.getInstance()).saveInstructions(0, cInstructions);
	}
	
	public void runProgram() {
		((ControlUnit)ControlUnit.getInstance()).runAll();
	}
	
}
