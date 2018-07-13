package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;

public class StatusRegister extends AbstractRegister{

	private InternalBus internalBus;
	
	private int zf, cf, of, sf, intf;
	
	private StatusRegister() {
		internalBus = (InternalBus) InternalBus.getInstance();
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new StatusRegister();
		return instance;
	}

	public int getZf() {
		return zf;
	}

	public int getCf() {
		return cf;
	}

	public int getOf() {
		return of;
	}

	public int getSf() {
		return sf;
	}

	public int getIntf() {
		return intf;
	}

	public void Sr_Ib() {
		internalBus.setData(data);
	}
	
	public void Cli() {
		intf = 1;
	}
	
	public void Sti() {
		intf = 0;
	}

}
