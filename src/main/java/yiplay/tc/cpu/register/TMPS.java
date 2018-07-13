package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;

public class TMPS extends AbstractRegister{
	
	private InternalBus internalBus;
	
	private TMPS() {
		internalBus = (InternalBus) InternalBus.getInstance();
	}

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new TMPS();
		return instance;
	}
	
	public void Tmps_Ib() {
		internalBus.setData(data);
	}

}
