package yiplay.tc.cpu.register;

import java.util.logging.Logger;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.ArithmeticLogicUnit;
import yiplay.util.Translate;

public class TMPE extends AbstractRegister{

	private final static Logger logger = Logger.getLogger( TMPE.class.getName() );
	
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
		logger.info(String.format("Tmpe_Clr signal launched === 0 -> TMPE\n"));
		setData((short) 0);
		((ArithmeticLogicUnit) ArithmeticLogicUnit.getInstance()).setOperand1((short) 0);
	}

	public void Tmpe_Set() {
		String data = "1111111111111111";
		short nData = Translate.toDecimalInteger(data);

		logger.info(String.format("Tmpe_Set signal launched === FFFF -> TMPE\n"));
		setData((short) nData);
		((ArithmeticLogicUnit) ArithmeticLogicUnit.getInstance()).setOperand1(this.data);
	}

}
