package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;

public class ProgramCounter extends AbstractRegister{
	
	private static AbstractComponent instance;
	
	private ProgramCounter() {
	}

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new ProgramCounter();
		return instance;
	}

	public void Pc_Ib() {
		System.out.println(String.format("Pc_Ib signal launched === PC -> %d -> IB",data));
		((InternalBus) InternalBus.getInstance()).setData(data);
	}

}
