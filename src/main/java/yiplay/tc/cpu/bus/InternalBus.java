package yiplay.tc.cpu.bus;

import yiplay.tc.AbstractBus;
import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.ArithmeticLogicUnit;
import yiplay.tc.cpu.register.*;

public class InternalBus extends AbstractBus{

	private static AbstractComponent instance;
	
	private InternalBus() {}

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new InternalBus();
		return instance;
	}

	@Override
	public void setData(short data) {
		this.data = data;
		((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).setOperand2(data);
	}
	
	public void Ib_Rx(int register) {
		System.out.println(String.format("Ib_R%d signal launched === IB -> %d -> R%d",register,data,register));
		((GeneralPurpousesRegisters) GeneralPurpousesRegisters.getInstance()).setData(register, data);
	}

	public void Ibh_Rxh(int register) {
		short lowByte = (short) ((((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).getData(register)) & 0xFF);
		short highByte = (short) (data & 0xFF00);
		short res = 0;
		res |= lowByte;
		res |= highByte;
		
		System.out.println(String.format("Ibh_R%dh signal launched === IBh -> %d -> R%dh",register,highByte,register));
		((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).setData(register,res);
	}

	public void Ibl_Rxl(int register) {
		short lowByte = (short) (data & 0xFF);
		short highByte = (short) ((((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).getData(register)) & 0xFF00);
		short res = 0;
		res |= lowByte;
		res |= highByte;
		
		System.out.println(String.format("Ibl_R%dl signal launched === IB -> %d -> R%d",register,lowByte,register));
		((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).setData(register,res);
	}

	public void Ib_Ir() {
		System.out.println(String.format("Ib_Ir signal launched === IB -> %d -> IR",data));
		((InstructionRegister) InstructionRegister.getInstance()).setData(data);
	}

	public void Ib_Pc() {
		System.out.println(String.format("Ib_Pc signal launched === IB -> %d -> PC ",data));
		((ProgramCounter) ProgramCounter.getInstance()).setData(data);
	}

	public void Ib_Mar() {
		System.out.println(String.format("Ib_Mar signal launched === IB -> %d -> MAR ",data));
		((MemoryAddressRegister) MemoryAddressRegister.getInstance()).setData(data);
	}

	public void Ib_Mdr() {
		System.out.println(String.format("Ib_Mdr signal launched === IB -> %d -> MDR ",data));
		((MemoryDataRegister) MemoryDataRegister.getInstance()).setData(data);
	}

	public void Ib_Tmpe() {
		System.out.println(String.format("Ib_Tmpe signal launched === IB -> %d -> TMPE ",data));
		((TMPE) TMPE.getInstance()).setData(data);
	}

	public void Ib_Sr() {
		System.out.println(String.format("Ib_Sr signal launched === IB -> %d -> SR ",data));
		((StatusRegister) StatusRegister.getInstance()).setData(data);
	}

}
