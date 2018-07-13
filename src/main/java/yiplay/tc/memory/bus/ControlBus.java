package yiplay.tc.memory.bus;

import yiplay.AbstractBus;
import yiplay.tc.AbstractComponent;
import yiplay.tc.memory.Memory;

public class ControlBus extends AbstractBus{

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new ControlBus();
		return instance;
	}

}
