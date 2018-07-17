package yiplay.tc.cpu.register;

import java.util.logging.Logger;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;

public class ProgramCounter extends AbstractRegister{

	private final static Logger logger = Logger.getLogger( ProgramCounter.class.getName() );
	
	private static AbstractComponent instance;
	
	private ProgramCounter() {
	}

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new ProgramCounter();
		return instance;
	}

	public void Pc_Ib() {
		logger.info(String.format("Pc_Ib signal launched === PC -> %d -> IB\n",data));
		((InternalBus) InternalBus.getInstance()).setData(data);
	}

}
