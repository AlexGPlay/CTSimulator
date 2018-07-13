package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;

public class GeneralPurpousesRegisters extends AbstractRegister{

	public static final int NUM_REGISTERS = 7;
	
	private short[] registers;
	
	private InternalBus internalBus;
	
	private GeneralPurpousesRegisters() {
		registers = new short[NUM_REGISTERS];
		
		internalBus = (InternalBus) InternalBus.getInstance();
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
		internalBus.setData(registers[register]);
	}
	
}
