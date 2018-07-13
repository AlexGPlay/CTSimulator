package yiplay.tc.memory.bus;

import yiplay.AbstractBus;
import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.register.MemoryDataRegister;

public class DataBus extends AbstractBus{

	private MemoryDataRegister mdr;
	
	private DataBus() {
		mdr = (MemoryDataRegister) MemoryDataRegister.getInstance();
	}

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new DataBus();
		return instance;
	}
	
	
	
}
