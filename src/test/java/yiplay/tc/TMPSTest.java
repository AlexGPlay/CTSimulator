package yiplay.tc;

import static org.junit.Assert.*;

import org.junit.Test;

import yiplay.tc.cpu.bus.InternalBus;
import yiplay.tc.cpu.register.TMPS;

public class TMPSTest {

	TMPS tmps = (TMPS) TMPS.getInstance();
	
	@Test
	public void setTest() {
		short data = 10;
		tmps.setData(data);
		tmps.Tmps_Ib();
		assertEquals(data,((InternalBus)InternalBus.getInstance()).getData());
	}

}
