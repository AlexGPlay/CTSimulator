package yiplay.tc.memory.bus;

import yiplay.tc.AbstractBus;
import yiplay.tc.AbstractComponent;

public class AddressBus extends AbstractBus{
	
	private static AbstractComponent instance;
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new AddressBus();
		return instance;
	}

}
