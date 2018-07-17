package yiplay.tc.cpu.register;

import java.util.logging.Logger;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;
import yiplay.tc.memory.bus.DataBus;

public class MemoryDataRegister extends AbstractRegister{
	
	private final static Logger logger = Logger.getLogger( MemoryDataRegister.class.getName() );
	
	private static AbstractComponent instance;
	
	private MemoryDataRegister() {
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new MemoryDataRegister();
		return instance;
	}
	
	@Override
	public void setData(short data) {
		this.data = data;
		((DataBus) DataBus.getInstance()).setData(data,true);
	}
	
	public void setData(short data, boolean db) {
		this.data = data;
	}
	
	public void Mdr_Ib() {
		logger.info(String.format("Mdr_Ib signal launched === MAR -> %d -> IB\n",data));
		((InternalBus) InternalBus.getInstance()).setData(data);
	}

}
