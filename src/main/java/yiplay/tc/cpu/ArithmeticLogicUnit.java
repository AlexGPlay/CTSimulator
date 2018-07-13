package yiplay.tc.cpu;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.register.StatusRegister;
import yiplay.tc.cpu.register.TMPS;

public class ArithmeticLogicUnit extends AbstractComponent{

	private TMPS tmps;
	private StatusRegister sr;
	
	private short operand1;
	private short operand2;
	private short res;
	
	private ArithmeticLogicUnit() {
		tmps = (TMPS) TMPS.getInstance();
		sr = (StatusRegister) StatusRegister.getInstance();
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new ArithmeticLogicUnit();
		return instance;
	}
	
	public void setOperand1(short operand1) {
		this.operand1 = operand1;
	}
	
	public void setOperand2(short operand2) {
		this.operand1 = operand2;
	}
	
	public void Add() {
		res = (short) (operand1 + operand2);
	}
	
	public void Sub() {
		res = (short) (operand1 - operand2);
	}
	
	public void Or() {
		res = (short) (operand1 | operand2);
	}
	
	public void And() {
		res = (short) (operand1 & operand2);
	}
	
	public void Xor() {
		res = (short) (operand1 ^ operand2);
	}
	
	public void Carry_In() {
		
	}
	
	public void Alu_Tmps() {
		tmps.setData(res);
	}
	
	public void Alu_Sr() {
		
	}

}
