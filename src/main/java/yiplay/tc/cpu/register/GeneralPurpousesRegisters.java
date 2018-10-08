package yiplay.tc.cpu.register;

import java.util.ArrayList;
import java.util.List;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;
import yiplay.util.observer.GPRNotifier;
import yiplay.util.observer.GPRObserver;

public class GeneralPurpousesRegisters extends AbstractRegister implements GPRNotifier{
	
	private static AbstractComponent instance;
	
	public static final int NUM_REGISTERS = 8;
	
	private short[] registers;
	private List<GPRObserver> observers;
	
	private GeneralPurpousesRegisters() {
		registers = new short[NUM_REGISTERS];
		registers[7] = -1;
		observers = new ArrayList<>();
		for(int i=0;i<registers.length;i++)
			notifyObservers(i, registers[i]);
	}

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new GeneralPurpousesRegisters();
		return instance;
	}
	
	public void resetRegisters() {
		registers = new short[NUM_REGISTERS];
		registers[7] = -1;
		for(int i=0;i<registers.length;i++)
			notifyObservers(i, registers[i]);
	}
	
	public short[] getRegisters() {
		return registers;
	}
	
	public void setData(int register, short data) {
		registers[register] = data;
		notifyObservers(register, data);
	}
	
	public short getData(int register) {
		return registers[register];
	}
	
	public void Rx_Ib(int register){
		System.out.println(String.format("R%d_Ib signal launched === R%d -> %d -> IB",register,register,registers[register]));
		((InternalBus) InternalBus.getInstance()).setData(registers[register]);
	}

	@Override
	public void notifyObservers(int register, short data) {
		for(GPRObserver ob : observers)
			ob.updateGPR(register,data);
	}

	@Override
	public void addObserver(GPRObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(GPRObserver observer) {
		observers.remove(observer);
	}
	
}
