package yiplay.tc.cpu.bus;

import yiplay.AbstractBus;
import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.ArithmeticLogicUnit;
import yiplay.tc.cpu.register.*;

public class InternalBus extends AbstractBus{

	private GeneralPurpousesRegisters generalPurpousesRegisters;
	private InstructionRegister instructionRegister;
	private ProgramCounter programCounter;
	private MemoryAddressRegister memoryAddressRegister;
	private MemoryDataRegister memoryDataRegister;
	private TMPE tmpe;
	private ArithmeticLogicUnit alu;
	private StatusRegister statusRegister;
	
	private InternalBus() {
		generalPurpousesRegisters = (GeneralPurpousesRegisters) GeneralPurpousesRegisters.getInstance();
		instructionRegister = (InstructionRegister) InstructionRegister.getInstance();
		programCounter = (ProgramCounter) ProgramCounter.getInstance();
		memoryAddressRegister = (MemoryAddressRegister) MemoryAddressRegister.getInstance();
		memoryDataRegister = (MemoryDataRegister) MemoryDataRegister.getInstance();
		tmpe = (TMPE) TMPE.getInstance();
		alu = (ArithmeticLogicUnit) ArithmeticLogicUnit.getInstance();
		statusRegister = (StatusRegister) StatusRegister.getInstance();
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new InternalBus();
		return instance;
	}
	
	public void Ib_Rx(int register) {
		generalPurpousesRegisters.setData(register, data);
	}
	
	public void Ibh_Rxh(int register) {
		byte highByte = (byte)((data >> 8) & 0xFF);

	}
	
	public void Ibl_Rxl(int register) {
		byte lowByte = (byte)(data & 0xFF);
	}
	
	public void ExtIrl_Ib() {
		
	}
	
	public void Ib_Ir() {
		instructionRegister.setData(data);
	}
	
	public void Ib_Pc() {
		programCounter.setData(data);
	}
	
	public void Ib_Mar() {
		memoryAddressRegister.setData(data);
	}
	
	public void Ib_Mdr() {
		memoryDataRegister.setData(data);
	}
	
	public void Ib_Tmpe() {
		tmpe.setData(data);
	}
	
	public void Ib_Sr() {
		statusRegister.setData(data);
	}
	
	public void Ib_Alu() {
		alu.setOperand2(data);
	}

}
