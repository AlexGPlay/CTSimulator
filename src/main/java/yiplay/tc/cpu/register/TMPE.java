package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.ArithmeticLogicUnit;

public class TMPE extends AbstractRegister{
	
	private ArithmeticLogicUnit alu;
	
	private TMPE() {
		alu = (ArithmeticLogicUnit) ArithmeticLogicUnit.getInstance();
	}

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new TMPE();
		return instance;
	}
	
	public void Tmpe_Clr() {
		setData((short) 0);
		alu.setOperand1((short) 0);
	}
	
	public void Tmpe_Set() {
		String data = "FFFF";
		short nData = Short.valueOf(data,16);
		
		setData(nData);
		alu.setOperand1(nData);
	}

}
