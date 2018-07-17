package yiplay.tc.cpu.register;

import java.util.logging.Logger;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;

public class TMPS extends AbstractRegister{
	
	private final static Logger logger = Logger.getLogger( TMPS.class.getName() );
	
	private static AbstractComponent instance;
	
	private TMPS() {
	}

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new TMPS();
		return instance;
	}
	
	public void Tmps_Ib() {
		logger.info(String.format("Tmps_Ib signal launched === TMPS -> %d -> IB\n",data));
		((InternalBus) InternalBus.getInstance()).setData(data);
	}

}
