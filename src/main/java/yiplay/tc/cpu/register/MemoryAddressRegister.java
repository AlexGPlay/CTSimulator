package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;
import yiplay.tc.memory.bus.AddressBus;

public class MemoryAddressRegister extends AbstractRegister{

	private AddressBus addressBus;
	
	private MemoryAddressRegister() {
		addressBus = (AddressBus) AddressBus.getInstance();
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new MemoryAddressRegister();
		return instance;
	}
	
	public void Mar_Sab() {
		addressBus.setData(data);
	}

}
