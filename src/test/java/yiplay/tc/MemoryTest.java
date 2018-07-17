package yiplay.tc;

import static org.junit.Assert.*;

import org.junit.Test;

import yiplay.tc.cpu.register.MemoryAddressRegister;
import yiplay.tc.cpu.register.MemoryDataRegister;
import yiplay.tc.memory.Memory;

public class MemoryTest {
	
	Memory memory = (Memory) Memory.getInstance();
	
	@Test
	public void readTest() {
		short position = 100;
		short res = memory.getMemory()[position];
		((MemoryAddressRegister)MemoryAddressRegister.getInstance()).setData(position);
		memory.read();
		assertEquals(res,((MemoryDataRegister)MemoryDataRegister.getInstance()).getData());
	}
	
	@Test
	public void writeTest() {
		short position = 100;
		short data = 10;
		((MemoryAddressRegister)MemoryAddressRegister.getInstance()).setData(position);
		((MemoryDataRegister)MemoryDataRegister.getInstance()).setData(data);
		memory.write();
		assertEquals(data,memory.getMemory()[position]);
	}

}
