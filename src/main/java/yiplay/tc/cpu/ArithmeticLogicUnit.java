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
	private boolean carryFlag;
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
		resetFlags();
		carryFlag = carryIn;
		String temp = add(Translate.toBinaryString(operand1, 16), Translate.toBinaryString(operand2, 16),16);
		res = Translate.toDecimalInteger(temp);	
		checkFlags();
		logger.info(String.format("Add signal launched === %d + %d = %d\n", operand1, operand2, res));
	}
	
	public void Sub() {
		operation = SUB;
		resetFlags();
		String temp = sub(Translate.toBinaryString(operand1, 16), Translate.toBinaryString(operand2, 16),16);
		res = Translate.toDecimalInteger(temp);	
		checkFlags();
		logger.info(String.format("Sub signal launched === %d - %d = %d\n", operand1, operand2, res));
	}
	
	public void Or() {
		operation = OR;
		resetFlags();
		String temp = or(Translate.toBinaryString(operand1, 16), Translate.toBinaryString(operand2, 16),16);
		res = Translate.toDecimalInteger(temp);	
		checkFlags();
		logger.info(String.format("Or signal launched === %d | %d = %d\n", operand1, operand2, res));
	}
	
	public void And() {
		operation = AND;
		resetFlags();
		String temp = and(Translate.toBinaryString(operand1, 16), Translate.toBinaryString(operand2, 16),16);
		res = Translate.toDecimalInteger(temp);	
		checkFlags();
		logger.info(String.format("And signal launched === %d & %d = %d\n", operand1, operand2, res));
	}
	
	public void Xor() {
		operation = XOR;
		resetFlags();
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
		int cf = carryFlag ? 1 : 0;
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
				if(carryFlag)
					res = '1' + res;
				else
					res = '0' + res;
				carryFlag = false;
			}

			else if(n1 == '0' && n2 == '1' || n1 == '1' && n2 == '0') {
				if(carryFlag) {
					res = '0' + res;
					carryFlag = true;
				}
				else {
					res = '1' + res;
					carryFlag = false;
				}
			}


			else {
				if(carryFlag) 
					res = '1' + res;
				else 
					res = '0' + res;
				
				carryFlag = true;
			}

		}
		
		int oCheck = (int)((int)operand1 + (int)operand2);
		if(oCheck > Short.MAX_VALUE || oCheck < Short.MIN_VALUE)
			overflowFlag = true;
		else 
			overflowFlag = false;
		
		return res;
	}
	
	private String sub(String op1, String op2, int bits) {
		String res = "";

		op1 = normalizeToNBits(op1,bits);
		op2 = normalizeToNBits(op2,bits);
		
		char[] nOp1 = op1.toCharArray();
		
		for(int i=bits-1;i>=0;i--) {
			char n1 = nOp1[i];
			char n2 = op2.charAt(i);

			if(n1 == '0' && n2 == '0') {
				res = '0' + res;
			}
			
			else if(n1 == '0' && n2 == '1') {
				res = '1' + res;
				nOp1 = lookForOne(nOp1, i-1);
			}
			
			else if(n1 == '1' && n2 == '0') {
				res = '1' + res;
			}
			
			else if(n1 == '1' && n2 == '1') {
				res = '0' + res;
			}

		}
		
		int oCheck = operand1 - operand2;
		if(oCheck > Short.MAX_VALUE || oCheck < Short.MIN_VALUE)
			overflowFlag = true;
		else 
			overflowFlag = false;
		
		return res;
	}
	
	private char[] lookForOne(char[] array, int startingPos) {
		for(int i=startingPos;i>=0;i--) {
			if(array[i] == '1') {
				array[i] = '0';
				carryFlag = false;
				return array;
			}
			else {
				array[i] = '1';
				continue;
			}
		}
		
		carryFlag = true;
		return array;
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
		
		carryIn = false;
	}
	
	private void resetFlags() {
		zeroFlag = false;
		signFlag = false;
		overflowFlag = false;
		carryFlag = false;
	}

	public void reset() {
		resetFlags();
		carryIn = false;
		operand1 = 0;
		operand2 = 0;
		res = 0;
		operation = 0;
	}

}
