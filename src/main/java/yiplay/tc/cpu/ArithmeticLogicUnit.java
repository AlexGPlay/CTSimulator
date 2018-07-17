package yiplay.tc.cpu;

import java.util.logging.Logger;

import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.register.StatusRegister;
import yiplay.tc.cpu.register.TMPS;
import yiplay.util.Translate;

public class ArithmeticLogicUnit extends AbstractComponent{
	
	private final static Logger logger = Logger.getLogger( ArithmeticLogicUnit.class.getName() );
	
	private short operand1;
	private short operand2;
	private short res;
	
	private boolean carryIn;
	private boolean zeroFlag;
	private boolean signFlag;
	private boolean overflowFlag;
	
	private int operation;
	
	public static final int ADD = 0;
	public static final int SUB = 1;
	public static final int OR = 2;
	public static final int AND = 3;
	public static final int XOR = 4;
	
	private static AbstractComponent instance;
	
	private ArithmeticLogicUnit() {}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new ArithmeticLogicUnit();
		return instance;
	}
	
	public void setOperand1(short operand1) {
		this.operand1 = operand1;
	}
	
	public void setOperand2(short operand2) {
		this.operand2 = operand2;
	}
	
	public int getOperation() {
		return operation;
	}
	
	public void Add() {
		operation = ADD;
		
		String temp = add(Translate.toBinaryString(operand1, 16), Translate.toBinaryString(operand2, 16),16);
		res = Translate.toDecimalInteger(temp);	
		checkFlags();
		logger.info(String.format("Add signal launched === %d + %d = %d\n", operand1, operand2, res));
	}
	
	public void Sub() {
		operation = SUB;
		
		short t2 = (short) (-1*operand2);
		String temp = add(Translate.toBinaryString(operand1, 16), Translate.toBinaryString(t2, 16),16);
		res = Translate.toDecimalInteger(temp);	
		checkFlags();
		logger.info(String.format("Sub signal launched === %d - %d = %d\n", operand1, operand2, res));
	}
	
	public void Or() {
		operation = OR;
		
		String temp = or(Translate.toBinaryString(operand1, 16), Translate.toBinaryString(operand2, 16),16);
		res = Translate.toDecimalInteger(temp);	
		checkFlags();
		logger.info(String.format("Or signal launched === %d | %d = %d\n", operand1, operand2, res));
	}
	
	public void And() {
		operation = AND;
		
		String temp = and(Translate.toBinaryString(operand1, 16), Translate.toBinaryString(operand2, 16),16);
		res = Translate.toDecimalInteger(temp);	
		checkFlags();
		logger.info(String.format("And signal launched === %d & %d = %d\n", operand1, operand2, res));
	}
	
	public void Xor() {
		operation = XOR;
		
		String temp = xor(Translate.toBinaryString(operand1, 16), Translate.toBinaryString(operand2, 16),16);
		res = Translate.toDecimalInteger(temp);	
		checkFlags();
		logger.info(String.format("Xor signal launched === %d ^ %d = %d\n", operand1, operand2, res));
	}
	
	public void Carry_In() {
		carryIn = true;
		logger.info(String.format("Carry_in signal launched === 1 -> CF\n", operand1, operand2, res));
	}
	
	public void Alu_Tmps() {
		((TMPS)TMPS.getInstance()).setData(res);
		logger.info(String.format("Alu_Tmps signal launched === ALU -> %d -> TMPS\n",res));
	}
	
	public void Alu_Sr() {
		int zf = zeroFlag ? 1 : 0;
		int cf = carryIn ? 1 : 0;
		int of = overflowFlag ? 1 : 0;
		int sf = signFlag ? 1 : 0;
		
		String z = Translate.toBinaryString((short) zf,4);
		String c = Translate.toBinaryString((short) cf,4);
		String o = Translate.toBinaryString((short) of,4);
		String s = Translate.toBinaryString((short) sf,4);
		
		short zcos = 0;
		zcos |= Short.valueOf(z, 2) << 12;
		zcos |= Short.valueOf(c, 2) << 8;
		zcos |= Short.valueOf(o, 2) << 4;
		zcos |= Short.valueOf(s, 2);
		logger.info(String.format("Alu_Sr signal launched === ALU -> X -> SR\n",res));
		
		((StatusRegister)StatusRegister.getInstance()).setData(zcos);
	}

	public boolean isCarryIn() {
		return carryIn;
	}

	public void setCarryIn(boolean carryIn) {
		this.carryIn = carryIn;
	}

	public short getOperand1() {
		return operand1;
	}

	public short getOperand2() {
		return operand2;
	}

	public short getRes() {
		return res;
	}
	
	private String add(String op1, String op2, int bits) {
		String res = "";

		op1 = normalizeToNBits(op1,bits);
		op2 = normalizeToNBits(op2,bits);
		
		for(int i=bits-1;i>=0;i--) {
			char n1 = op1.charAt(i);
			char n2 = op2.charAt(i);

			if(n1 == '0' && n2 == '0') {
				if(carryIn)
					res = '1' + res;
				else
					res = '0' + res;
				carryIn = false;
			}

			else if(n1 == '0' && n2 == '1' || n1 == '1' && n2 == '0') {
				if(carryIn) {
					res = '0' + res;
					carryIn = true;
				}
				else {
					res = '1' + res;
					carryIn = false;
				}
			}


			else {
				if(carryIn) 
					res = '1' + res;
				else 
					res = '0' + res;
				
				carryIn = true;
			}

		}
		
		int oCheck = operand1 + operand2;
		if(Integer.toBinaryString(oCheck).length()>bits)
			overflowFlag = true;
		else 
			overflowFlag = false;
		
		return res;
	}
	
	private String or(String op1, String op2 ,int bits) {
		String res = "";

		op1 = normalizeToNBits(op1,bits);
		op2 = normalizeToNBits(op2,bits);
		
		for(int i=bits-1;i>=0;i--) {
			char n1 = op1.charAt(i);
			char n2 = op2.charAt(i);
			
			if(n1 == '0' && n2 == '0')
				res = '0' + res;
			
			else
				res = '1' + res;			
		}
		
		return res;
	}
	
	private String and(String op1, String op2 ,int bits) {
		String res = "";

		op1 = normalizeToNBits(op1,bits);
		op2 = normalizeToNBits(op2,bits);
		
		for(int i=bits-1;i>=0;i--) {
			char n1 = op1.charAt(i);
			char n2 = op2.charAt(i);
			
			if(n1 == '1' && n2 == '1')
				res = '1' + res;
			
			else
				res = '0' + res;			
		}
		
		return res;
	}
	
	private String xor(String op1, String op2, int bits) {
		String res = "";

		op1 = normalizeToNBits(op1,bits);
		op2 = normalizeToNBits(op2,bits);
		
		for(int i=bits-1;i>=0;i--) {
			char n1 = op1.charAt(i);
			char n2 = op2.charAt(i);
			
			if(n1 == n2)
				res = '0' + res;
			
			else
				res = '1' + res;			
		}
		
		return res;
	}
	
	private String normalizeToNBits(String op, int nBits) {
		while(op.length()<nBits)
			op = "0" + op;
		return op;
	}
	
	private void checkFlags() {
		if(res == 0)
			zeroFlag = true;
		else
			zeroFlag = false;
		
		if(res >= 0)
			signFlag = false;
		else
			signFlag = true;
		
	}

}
