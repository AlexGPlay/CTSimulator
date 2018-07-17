package yiplay.tc;

import static org.junit.Assert.*;

import org.junit.Test;

import yiplay.tc.cpu.ArithmeticLogicUnit;
import yiplay.tc.cpu.register.TMPE;

public class TMPETest {

	TMPE tmpe = (TMPE) TMPE.getInstance();
	
	@Test
	public void setTest() {
		short data = 10;
		tmpe.setData(data);
		assertEquals(data,((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).getOperand1());
	}
	
	@Test
	public void Tmpe_ClrTest() {
		short res = 0;
		tmpe.Tmpe_Clr();
		assertEquals(res,((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).getOperand1());
		assertEquals(res,tmpe.getData());
	}
	
	@Test
	public void Tmpe_SetTest() {
		short res = -1;
		tmpe.Tmpe_Set();
		assertEquals(res,((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).getOperand1());
		assertEquals(res,tmpe.getData());
	}

}
