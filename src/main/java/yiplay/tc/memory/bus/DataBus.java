package yiplay.tc.memory.bus;

import yiplay.tc.AbstractBus;
import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.register.MemoryDataRegister;

public class DataBus extends AbstractBus{

	private static AbstractComponent instance;
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new DataBus();
		return instance;
	}
	
	@Override
	public void setData(short data) {
		this.data = data;
		((MemoryDataRegister)MemoryDataRegister.getInstance()).setData(data,true);
	}
	
	public void setData(short data, boolean mdr) {
		this.data = data;
	}
	
}
