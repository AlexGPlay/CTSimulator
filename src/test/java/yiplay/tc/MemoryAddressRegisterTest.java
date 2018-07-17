package yiplay.tc;

import static org.junit.Assert.*;

import org.junit.Test;

import yiplay.tc.cpu.register.MemoryAddressRegister;
import yiplay.tc.memory.bus.AddressBus;

public class MemoryAddressRegisterTest {

	MemoryAddressRegister mar = (MemoryAddressRegister) MemoryAddressRegister.getInstance();
	
	@Test
	public void testSet() {
		
		short data = 30;
		mar.setData(data);
		assertEquals(((AddressBus)AddressBus.getInstance()).getData(),data);
		
	}

}
