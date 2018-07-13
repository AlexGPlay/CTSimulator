package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;
import yiplay.tc.memory.bus.DataBus;

public class MemoryDataRegister extends AbstractRegister{

	private InternalBus internalBus;
	private DataBus dataBus;
	
	private MemoryDataRegister() {
		internalBus = (InternalBus) InternalBus.getInstance();
		dataBus = (DataBus) DataBus.getInstance();
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new MemoryDataRegister();
		return instance;
	}
	
	public void Mdr_Ib() {
		internalBus.setData(data);
	}
	
	public void Mdr_Sdb() {
		dataBus.setData(data);
	}

}
