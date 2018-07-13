package yiplay.tc.memory;

import java.util.List;

import yiplay.tc.AbstractComponent;
import yiplay.tc.memory.bus.AddressBus;
import yiplay.tc.memory.bus.DataBus;

public class Memory extends AbstractComponent{

	private DataBus dataBus;
	private AddressBus addressBus;
	
	private short[] memory;
	
	private Memory() {
		dataBus = (DataBus) DataBus.getInstance();
		addressBus = (AddressBus) AddressBus.getInstance();
		memory = new short[Short.MAX_VALUE];
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new Memory();
		return instance;
	}
	
	public void saveInstructions(int from, List<Short> instructions) {
		int index = 0;
		
		for(int i=from;i<instructions.size();i++) {
			memory[i] = instructions.get(index);
			index++;
		}
	}
	
	public void read() {
		dataBus.setData(memory[ addressBus.getData() ]);
	}
	
	public void write() {
		memory[ addressBus.getData() ] = dataBus.getData();
	}
	
}
