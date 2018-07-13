package yiplay;

import yiplay.tc.AbstractComponent;

public abstract class AbstractBus extends AbstractComponent{

	protected short data;

	public short getData() {
		return data;
	}

	public void setData(short data) {
		this.data = data;
	}
	
}
