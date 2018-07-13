package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;

public abstract class AbstractRegister extends AbstractComponent{
	
	protected short data;

	public short getData() {
		return data;
	}

	public void setData(short data) {
		this.data = data;
	}
	
}
