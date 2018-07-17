package yiplay.tc.memory;

import java.util.List;
import java.util.logging.Logger;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.ControlUnit;
import yiplay.tc.memory.bus.AddressBus;
import yiplay.tc.memory.bus.DataBus;
import yiplay.util.Translate;

public class Memory extends AbstractComponent{
	
	private final static Logger logger = Logger.getLogger( ControlUnit.class.getName() );
	
	public static final int MAX_POS = 65535;
	
	private short[] memory;
	private static AbstractComponent instance;
	
	private Memory() {
		memory = new short[MAX_POS];
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new Memory();
		return instance;
	}
	
	public short[] getMemory() {
		return memory;
	}
	
	public void saveInstructions(int from, List<Short> instructions) {
		int index = 0;
		
		for(int i=from;i<instructions.size();i++) {
			memory[i] = instructions.get(index);
			index++;
		}
	}
	
	public void read() {
		short aB = ((AddressBus)AddressBus.getInstance()).getData();
		String bP = Translate.toBinaryString(aB, 16);
		
		logger.info(String.format("Read signal launched === Preparing to send %d from %s position to MDR",
				memory[Translate.toMemoryPosition(bP)], bP));
		
		((DataBus)DataBus.getInstance()).setData(memory[ Translate.toMemoryPosition(bP) ]);
	}
	
	public void write() {
		short aB = ((AddressBus)AddressBus.getInstance()).getData();
		String bP = Translate.toBinaryString(aB, 16);
		
		logger.info(String.format("Write signal launched === Preparing to write %d into %d position",
				((DataBus)DataBus.getInstance()).getData(), Translate.toMemoryPosition(bP)));
		
		memory[ Translate.toMemoryPosition(bP) ] = ((DataBus)DataBus.getInstance()).getData();
	}
	
}
