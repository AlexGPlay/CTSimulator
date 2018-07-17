package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;
import yiplay.tc.memory.bus.AddressBus;

public class MemoryAddressRegister extends AbstractRegister{
	
	private static AbstractComponent instance;
	
	private MemoryAddressRegister() {}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new MemoryAddressRegister();
		return instance;
	}
	
	@Override
	public void setData(short data) {
		this.data = data;
		((AddressBus)AddressBus.getInstance()).setData(data);
	}

}
