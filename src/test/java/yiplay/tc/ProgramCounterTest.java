package yiplay.tc;

import static org.junit.Assert.*;

import org.junit.Test;

import yiplay.tc.cpu.bus.InternalBus;
import yiplay.tc.cpu.register.ProgramCounter;

public class ProgramCounterTest {

	ProgramCounter pc = (ProgramCounter) ProgramCounter.getInstance();
	
	@Test
	public void Pc_IbTest() {
		short data = 10;
		pc.setData(data);
		pc.Pc_Ib();
		assertEquals(data,((InternalBus)InternalBus.getInstance()).getData());
	}

}
