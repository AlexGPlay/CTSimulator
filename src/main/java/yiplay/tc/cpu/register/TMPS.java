package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;

public class TMPS extends AbstractRegister{
		
	private static AbstractComponent instance;
	
	private TMPS() {
	}

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new TMPS();
		return instance;
	}
	
	public void Tmps_Ib() {
		System.out.println(String.format("Tmps_Ib signal launched === TMPS -> %d -> IB",data));
		((InternalBus) InternalBus.getInstance()).setData(data);
	}

}
