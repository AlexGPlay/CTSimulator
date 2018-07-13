package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;

public class ProgramCounter extends AbstractRegister{

	private InternalBus internalBus;
	
	private ProgramCounter() {
		internalBus = (InternalBus) InternalBus.getInstance();
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new ProgramCounter();
		return instance;
	}
	
	public void Pc_Ib() {
		internalBus.setData(data);
	}

}
