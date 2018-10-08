package yiplay.tc;

import static org.junit.Assert.*;

import org.junit.Test;

import yiplay.tc.cpu.ControlUnit;

public class ControlUnitTest {

	ControlUnit cUnit = (ControlUnit) ControlUnit.getInstance();
	
	@Test
	public void testFin() {
		cUnit.Fin();
		assertEquals(3,cUnit.getCycles().length);
	}
	
}
