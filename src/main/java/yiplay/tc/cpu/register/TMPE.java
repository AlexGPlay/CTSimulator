package yiplay.tc.cpu.register;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.ArithmeticLogicUnit;
import yiplay.util.Translate;

public class TMPE extends AbstractRegister{
	
	private static AbstractComponent instance;
	
	private TMPE() {
	}
	
	@Override
	public void setData(short data) {
		this.data = data;
		((ArithmeticLogicUnit) ArithmeticLogicUnit.getInstance()).setOperand1(data);
	}

	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new TMPE();
		return instance;
	}

	public void Tmpe_Clr() {
		System.out.println(String.format("Tmpe_Clr signal launched === 0 -> TMPE"));
		setData((short) 0);
		((ArithmeticLogicUnit) ArithmeticLogicUnit.getInstance()).setOperand1((short) 0);
	}

	public void Tmpe_Set() {
		String data = "1111111111111111";
		short nData = Translate.toDecimalInteger(data);

		System.out.println(String.format("Tmpe_Set signal launched === FFFF -> TMPE"));
		setData((short) nData);
		((ArithmeticLogicUnit) ArithmeticLogicUnit.getInstance()).setOperand1(this.data);
	}

}
