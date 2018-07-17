package yiplay.tc;

import static org.junit.Assert.*;

import org.junit.Test;

import yiplay.tc.cpu.ArithmeticLogicUnit;
import yiplay.tc.cpu.bus.InternalBus;
import yiplay.tc.cpu.register.*;
import yiplay.util.Translate;

public class InternalBusTest {

	InternalBus internalBus = (InternalBus) InternalBus.getInstance();

	@Test
	public void setDataTest() {

		short data = 10;
		internalBus.setData(data);
		assertEquals(data, ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).getOperand2());

		data = 5;
		internalBus.setData(data);
		assertEquals(data, ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).getOperand2());

	}

	@Test
	public void Ib_IrTest() {

		short data = 10;
		internalBus.setData(data);
		internalBus.Ib_Ir();
		assertEquals(data, ((InstructionRegister)InstructionRegister.getInstance()).getData());

		data = 5;
		internalBus.setData(data);
		internalBus.Ib_Ir();
		assertEquals(data, ((InstructionRegister)InstructionRegister.getInstance()).getData());

	}

	@Test
	public void Ib_MarTest() {

		short data = 10;
		internalBus.setData(data);
		internalBus.Ib_Mar();
		assertEquals(data, ((MemoryAddressRegister)MemoryAddressRegister.getInstance()).getData());

		data = 5;
		internalBus.setData(data);
		internalBus.Ib_Mar();
		assertEquals(data, ((MemoryAddressRegister)MemoryAddressRegister.getInstance()).getData());

	}

	@Test
	public void Ib_MdrTest() {

		short data = 10;
		internalBus.setData(data);
		internalBus.Ib_Mdr();
		assertEquals(data, ((MemoryDataRegister)MemoryDataRegister.getInstance()).getData());

		data = 5;
		internalBus.setData(data);
		internalBus.Ib_Mdr();
		assertEquals(data, ((MemoryDataRegister)MemoryDataRegister.getInstance()).getData());

	}

	@Test
	public void Ib_PcTest() {

		short data = 10;
		internalBus.setData(data);
		internalBus.Ib_Pc();
		assertEquals(data, ((ProgramCounter)ProgramCounter.getInstance()).getData());

		data = 5;
		internalBus.setData(data);
		internalBus.Ib_Pc();
		assertEquals(data, ((ProgramCounter)ProgramCounter.getInstance()).getData());

	}

	@Test
	public void Ib_RxTest() {

		for(int i=0;i<7;i++) {
			short data = 10;
			internalBus.setData(data);
			internalBus.Ib_Rx(i);
			assertEquals(data, ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).getData(i));

			data = 5;
			internalBus.setData(data);
			internalBus.Ib_Rx(i);
			assertEquals(data, ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).getData(i));
		}

	}

	@Test
	public void Ib_SrTest() {
		short flags = Translate.toDecimalInteger("0001000100010001");
		internalBus.setData(flags);
		internalBus.Ib_Sr();
		
		StatusRegister sr = (StatusRegister) StatusRegister.getInstance();
		
		assertEquals(1,sr.getZf());
		assertEquals(1,sr.getCf());
		assertEquals(1,sr.getOf());
		assertEquals(1,sr.getSf());
		
		flags = Translate.toDecimalInteger("0000000100010001");
		internalBus.setData(flags);
		internalBus.Ib_Sr();
		
		assertEquals(0,sr.getZf());
		assertEquals(1,sr.getCf());
		assertEquals(1,sr.getOf());
		assertEquals(1,sr.getSf());
		
		flags = Translate.toDecimalInteger("0000000000010001");
		internalBus.setData(flags);
		internalBus.Ib_Sr();
		
		assertEquals(0,sr.getZf());
		assertEquals(0,sr.getCf());
		assertEquals(1,sr.getOf());
		assertEquals(1,sr.getSf());
		
		flags = Translate.toDecimalInteger("0000000000000001");
		internalBus.setData(flags);
		internalBus.Ib_Sr();
		
		assertEquals(0,sr.getZf());
		assertEquals(0,sr.getCf());
		assertEquals(0,sr.getOf());
		assertEquals(1,sr.getSf());
		
		flags = Translate.toDecimalInteger("0000000000000000");
		internalBus.setData(flags);
		internalBus.Ib_Sr();
		
		assertEquals(0,sr.getZf());
		assertEquals(0,sr.getCf());
		assertEquals(0,sr.getOf());
		assertEquals(0,sr.getSf());
	}

	@Test
	public void Ib_TmpeTest() {

		short data = 10;
		internalBus.setData(data);
		internalBus.Ib_Tmpe();
		assertEquals(data, ((TMPE)TMPE.getInstance()).getData());

		data = 5;
		internalBus.setData(data);
		internalBus.Ib_Tmpe();
		assertEquals(data, ((TMPE)TMPE.getInstance()).getData());

	}

	@Test
	public void Ibh_RxhTest() {

		for(int i=0;i<8;i++) {
			short data = -1;
			internalBus.setData(data);
			internalBus.Ibh_Rxh(i);
			
			short lowByte = (short) ((((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).getData(i)) & 0xFF);
			short highByte = (short) (data & 0xFF00);
			short res = 0;
			res |= lowByte;
			res |= highByte;
			
			assertEquals(res, ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).getData(i));
		}
		
	}
	
	@Test
	public void Ibl_RxlText() {
		
		for(int i=0;i<8;i++) {
			short data = -1;
			internalBus.setData(data);
			internalBus.Ibl_Rxl(i);
			
			short lowByte = (short) (data & 0xFF);
			short highByte = (short) ((((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).getData(i)) & 0xFF00);
			short res = 0;
			res |= lowByte;
			res |= highByte;
			
			assertEquals(res, ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).getData(i));
		}
		
	}

}
