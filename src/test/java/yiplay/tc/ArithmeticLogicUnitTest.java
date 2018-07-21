package yiplay.tc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import yiplay.tc.cpu.ArithmeticLogicUnit;
import yiplay.tc.cpu.register.StatusRegister;
import yiplay.tc.cpu.register.TMPS;

public class ArithmeticLogicUnitTest {

	private ArithmeticLogicUnit alu = (ArithmeticLogicUnit) ArithmeticLogicUnit.getInstance();
	
	@After
	public void reset() {
		alu.reset();
		((StatusRegister)StatusRegister.getInstance()).reset();
	}
	
	@Test
	public void addTest() {
		short operand1 = 10;
		short operand2 = 20;
		short res = 30;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Add();
		
		assertEquals(res,alu.getRes());
		
		operand1 = 20;
		operand2 = 10;
		res = 30;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Add();
		
		assertEquals(res,alu.getRes());
		
		operand1 = 20;
		operand2 = -10;
		res = 10;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Add();
		
		assertEquals(res,alu.getRes());
		
		operand1 = -20;
		operand2 = -10;
		res = -30;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Add();
		
		assertEquals(res,alu.getRes());
		
		operand1 = -20;
		operand2 = 10;
		res = -10;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Add();
		
		assertEquals(res,alu.getRes());
	}
	
	@Test
	public void subTest() {
		short operand1 = 10;
		short operand2 = 20;
		short res = -10;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Sub();
		
		assertEquals(res,alu.getRes());
		
		operand1 = 20;
		operand2 = 10;
		res = 10;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Sub();
		
		assertEquals(res,alu.getRes());
		
		operand1 = 20;
		operand2 = -10;
		res = 30;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Sub();
		
		assertEquals(res,alu.getRes());
		
		operand1 = -20;
		operand2 = -10;
		res = -10;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Sub();
		
		assertEquals(res,alu.getRes());
		
		operand1 = -20;
		operand2 = 10;
		res = -30;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Sub();
		
		assertEquals(res,alu.getRes());
	}
	
	@Test
	public void orTest() {
		short operand1 = 10;
		short operand2 = 20;
		short res = 30;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Or();
		
		assertEquals(res,alu.getRes());
	}
	
	@Test
	public void andTest() {
		short operand1 = 10;
		short operand2 = 20;
		short res = 0;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.And();
		
		assertEquals(res,alu.getRes());
	}
	
	@Test
	public void xorTest() {
		short operand1 = 10;
		short operand2 = 20;
		short res = 30;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Xor();
		
		assertEquals(res,alu.getRes());
	}
	
	@Test
	public void Alu_TmpsTest() {
		short operand1 = 10;
		short operand2 = 20;
		short res = 30;
		
		alu.setOperand1(operand1);
		alu.setOperand2(operand2);
		alu.Add();

		alu.Alu_Tmps();
		
		assertEquals( res , ((TMPS)TMPS.getInstance()).getData() );
	}
	
	@Test
	public void Alu_SrTest() {
		assertEquals(0,((StatusRegister)StatusRegister.getInstance()).getSf());
		alu.setOperand1((short) 10);
		alu.setOperand2((short) 20);
		alu.Sub();
		alu.Alu_Sr();
		assertEquals(1,((StatusRegister)StatusRegister.getInstance()).getSf());
		alu.setOperand1((short) 10);
		alu.setOperand2((short) 20);
		alu.Add();
		alu.Alu_Sr();
		assertEquals(0,((StatusRegister)StatusRegister.getInstance()).getSf());
		
		assertEquals(0,((StatusRegister)StatusRegister.getInstance()).getZf());
		alu.setOperand1((short) 20);
		alu.setOperand2((short) 20);
		alu.Sub();
		alu.Alu_Sr();
		assertEquals(1,((StatusRegister)StatusRegister.getInstance()).getZf());
		alu.setOperand1((short) 10);
		alu.setOperand2((short) 20);
		alu.Sub();
		alu.Alu_Sr();
		assertEquals(0,((StatusRegister)StatusRegister.getInstance()).getZf());
		
		assertEquals(0,((StatusRegister)StatusRegister.getInstance()).getOf());
		alu.setOperand1((short) -32768);
		alu.setOperand2((short) 1);
		alu.Sub();
		alu.Alu_Sr();
		assertEquals(1,((StatusRegister)StatusRegister.getInstance()).getOf());
		alu.setOperand1((short) 10);
		alu.setOperand2((short) 20);
		alu.Sub();
		alu.Alu_Sr();
		assertEquals(0,((StatusRegister)StatusRegister.getInstance()).getOf());
		alu.setOperand1((short) 32767);
		alu.setOperand2((short) 1);
		alu.Add();
		alu.Alu_Sr();
		assertEquals(1,((StatusRegister)StatusRegister.getInstance()).getOf());
		
	}

}
