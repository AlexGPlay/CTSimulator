package yiplay.tc;

import static org.junit.Assert.*;

import org.junit.Test;

import yiplay.tc.cpu.bus.InternalBus;
import yiplay.tc.cpu.register.StatusRegister;
import yiplay.util.Translate;

public class StatusRegisterTest {

	StatusRegister sr = (StatusRegister) StatusRegister.getInstance();
	
	@Test
	public void cliTest() {
		sr.Cli();
		assertEquals(1,sr.getIntf());
	}
	
	@Test
	public void stiTest() {
		sr.Sti();
		assertEquals(0,sr.getIntf());
	}
	
	@Test
	public void Sr_IbTest() {
		short flags = Translate.toDecimalInteger("0001000100010001");
		sr.setData(flags);
		sr.Sr_Ib();
		assertEquals(flags,((InternalBus)InternalBus.getInstance()).getData());
		
		flags = Translate.toDecimalInteger("0000000100010001");
		sr.setData(flags);
		sr.Sr_Ib();
		assertEquals(flags,((InternalBus)InternalBus.getInstance()).getData());
		
		flags = Translate.toDecimalInteger("0000000000010001");
		sr.setData(flags);
		sr.Sr_Ib();
		assertEquals(flags,((InternalBus)InternalBus.getInstance()).getData());
		
		flags = Translate.toDecimalInteger("0000000000000001");
		sr.setData(flags);
		sr.Sr_Ib();
		assertEquals(flags,((InternalBus)InternalBus.getInstance()).getData());
		
		flags = Translate.toDecimalInteger("0000000000000000");
		sr.setData(flags);
		sr.Sr_Ib();
		assertEquals(flags,((InternalBus)InternalBus.getInstance()).getData());
	}

}
