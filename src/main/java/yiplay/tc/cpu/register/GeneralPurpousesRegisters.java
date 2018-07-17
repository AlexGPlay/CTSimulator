package yiplay.tc.cpu.register;

import java.util.logging.Logger;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;

public class GeneralPurpousesRegisters extends AbstractRegister{

	private final static Logger logger = Logger.getLogger( GeneralPurpousesRegisters.class.getName() );
	
	private static AbstractComponent instance;
	
	public static final int NUM_REGISTERS = 8;
	
	private short[] registers;
	
	private GeneralPurpousesRegisters() {
		registers = new short[NUM_REGISTERS];
		registers[7] = -1;
	}

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new GeneralPurpousesRegisters();
		return instance;
	}
	
	public void setData(int register, short data) {
		registers[register] = data;
	}
	
	public short getData(int register) {
		return registers[register];
	}
	
	public void Rx_Ib(int register){
		logger.info(String.format("R%d_Ib signal launched === R%d -> %d -> IB\n",register,register,registers[register]));
		((InternalBus) InternalBus.getInstance()).setData(registers[register]);
	}
	
}
