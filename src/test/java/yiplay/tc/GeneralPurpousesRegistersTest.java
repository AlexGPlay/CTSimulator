package yiplay.tc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import yiplay.tc.cpu.bus.InternalBus;
import yiplay.tc.cpu.register.GeneralPurpousesRegisters;

public class GeneralPurpousesRegistersTest {

	GeneralPurpousesRegisters registers = (GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance();
	
	@Test
	public void Rx_IbTest() {
		
		for(int i=0;i<8;i++) {
			short data = 10;
			registers.setData(i, data);
			registers.Rx_Ib(i);
			assertEquals(data, ((InternalBus)InternalBus.getInstance()).getData());
		}
		
	}
	
}
