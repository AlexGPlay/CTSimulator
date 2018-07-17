package yiplay.tc;

import static org.junit.Assert.*;

import org.junit.Test;

import yiplay.tc.cpu.bus.InternalBus;
import yiplay.tc.cpu.register.MemoryDataRegister;
import yiplay.tc.memory.bus.DataBus;

public class MemoryDataRegisterTest {

	MemoryDataRegister mdr = (MemoryDataRegister) MemoryDataRegister.getInstance();
	
	@Test
	public void setDataTest() {
		
		short data = 30;
		mdr.setData(data);
		assertEquals(data,((DataBus)DataBus.getInstance()).getData());
		
	}
	
	@Test
	public void Mdr_IbTest() {
		
		short data = 30;
		mdr.setData(data);
		mdr.Mdr_Ib();
		assertEquals(data,((InternalBus)InternalBus.getInstance()).getData());
		
	}

}
