package yiplay.tc.memory.bus;

import yiplay.AbstractBus;
import yiplay.tc.AbstractComponent;

public class AddressBus extends AbstractBus{
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new AddressBus();
		return instance;
	}

}
