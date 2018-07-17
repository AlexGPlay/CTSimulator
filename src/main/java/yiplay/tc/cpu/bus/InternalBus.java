package yiplay.tc.cpu.bus;

import java.util.logging.Logger;

import yiplay.tc.AbstractBus;
import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.ArithmeticLogicUnit;
import yiplay.tc.cpu.register.*;

public class InternalBus extends AbstractBus{

	private final static Logger logger = Logger.getLogger( InternalBus.class.getName() );
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
		logger.info(String.format("Ib_R%d signal launched === IB -> %d -> R%d\n",register,data,register));
		((GeneralPurpousesRegisters) GeneralPurpousesRegisters.getInstance()).setData(register, data);
	}

	public void Ibh_Rxh(int register) {
		short lowByte = (short) ((((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).getData(register)) & 0xFF);
		short highByte = (short) (data & 0xFF00);
		short res = 0;
		res |= lowByte;
		res |= highByte;
		
		logger.info(String.format("Ibh_R%dh signal launched === IBh -> %d -> R%dh\n",register,highByte,register));
		((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).setData(register,res);
	}

	public void Ibl_Rxl(int register) {
		short lowByte = (short) (data & 0xFF);
		short highByte = (short) ((((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).getData(register)) & 0xFF00);
		short res = 0;
		res |= lowByte;
		res |= highByte;
		
		logger.info(String.format("Ibl_R%dl signal launched === IB -> %d -> R%d\n",register,lowByte,register));
		((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).setData(register,res);
	}

	public void Ib_Ir() {
		logger.info(String.format("Ib_Ir signal launched === IB -> %d -> IR\n",data));
		((InstructionRegister) InstructionRegister.getInstance()).setData(data);
	}

	public void Ib_Pc() {
		logger.info(String.format("Ib_Pc signal launched === IB -> %d -> PC\n",data));
		((ProgramCounter) ProgramCounter.getInstance()).setData(data);
	}

	public void Ib_Mar() {
		logger.info(String.format("Ib_Mar signal launched === IB -> %d -> MAR\n",data));
		((MemoryAddressRegister) MemoryAddressRegister.getInstance()).setData(data);
	}

	public void Ib_Mdr() {
		logger.info(String.format("Ib_Mdr signal launched === IB -> %d -> MDR\n",data));
		((MemoryDataRegister) MemoryDataRegister.getInstance()).setData(data);
	}

	public void Ib_Tmpe() {
		logger.info(String.format("Ib_Tmpe signal launched === IB -> %d -> TMPE\n",data));
		((TMPE) TMPE.getInstance()).setData(data);
	}

	public void Ib_Sr() {
		logger.info(String.format("Ib_Sr signal launched === IB -> %d -> SR\n",data));
		((StatusRegister) StatusRegister.getInstance()).setData(data);
	}

}
