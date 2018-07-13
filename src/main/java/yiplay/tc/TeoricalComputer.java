package yiplay.tc;

import java.util.ArrayList;
import java.util.List;

import yiplay.tc.memory.Memory;

public class TeoricalComputer {

	public void loadInstructions(List<String> instructions) {
		List<Short> cInstructions = new ArrayList<Short>();
		
		for(String s : instructions) {
			short temp = Short.valueOf(s,2);
			cInstructions.add(temp);
		}
		
		((Memory)Memory.getInstance()).saveInstructions(0, cInstructions);
	}
	
}
