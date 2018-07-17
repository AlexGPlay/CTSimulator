package yiplay.tc.cpu.register;

import java.util.logging.Logger;

import yiplay.language.ast.ASTNode;
import yiplay.language.ast.expression.*;
import yiplay.language.ast.statement.*;
import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.ControlUnit;
import yiplay.tc.cpu.bus.InternalBus;
import yiplay.util.Translate;

public class InstructionRegister extends AbstractRegister {
	
	private final static Logger logger = Logger.getLogger( InstructionRegister.class.getName() );
	
	private ASTNode actualInstruction;
	
	private static AbstractComponent instance;
	
	private InstructionRegister() {}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new InstructionRegister();
		return instance;
	}
	
	public ASTNode getActualInstruction() {
		return actualInstruction;
	}
	
	@Override
	public void setData(short data) {
		this.data = data;
		String ins = Translate.toBinaryString(data, 16);
		while(ins.length()<16)
			ins = "0" + ins;
		actualInstruction = translateBinaryToInstruction(ins);
		
		if(actualInstruction == null)
			return;
			
		actualInstruction.accept((ControlUnit)ControlUnit.getInstance(), null);
		
		System.out.println(String.format("Preparing to execute: %s\n", actualInstruction));
	}
	
	public void Irl_Ibl() {
		short lowByte = (short) (data & 0xFF);
		short highByte = (short) ((((InternalBus)InternalBus.getInstance()).getData()) & 0xFF00);
		short res = 0;
		res |= lowByte;
		res |= highByte;
		
		logger.info(String.format("Irl_Ibl signal launched === IRl -> %d -> IBl\n",lowByte));
		((InternalBus)InternalBus.getInstance()).setData(res);
	}
	
	public void Irl_Ibh() {
		short lowByte = (short) ((((InternalBus)InternalBus.getInstance()).getData()) & 0xFF);
		short highByte = (short) (data & 0xFF);
		short res = 0;
		res |= lowByte;
		res |= highByte;
		
		logger.info(String.format("Irl_Ibh signal launched === IRl -> %d -> IBh\n",lowByte));
		((InternalBus)InternalBus.getInstance()).setData(res);
	}
	
	public void ExtIrl_Ib() {
		short lowByte = (short) (data & 0xFF);
		logger.info(String.format("ExtIrl_Ibh signal launched === EXTIRl -> %d -> IBh\n", lowByte));
		((InternalBus)InternalBus.getInstance()).setData(lowByte);
	}

	public ASTNode translateBinaryToInstruction(String instruccion) {
		ASTNode instruction = null;

		switch(instruccion.substring(0,5)) {

		case "00000": //NOP
			instruction = generateNop(instruccion);
			break;

		case "00001": //MOV RD,RS
			instruction = generateMov0(instruccion);
			break;

		case "00010": //MOV RD, [RI]
			instruction = generateMov1(instruccion);
			break;

		case "00011": //MOV [RI], RS
			instruction = generateMov2(instruccion);
			break;

		case "00100": //MOVL RD, INM8
			instruction = generateMovl(instruccion);
			break;

		case "00101": //MOVH RD, INM8
			instruction = generateMovh(instruccion);
			break;

		case "00110": //PUSH RS
			instruction = generatePush(instruccion);
			break;

		case "00111": //POP RD
			instruction = generatePop(instruccion);
			break;

		case "01000": //ADD RD, R1, R2
			instruction = generateAdd(instruccion);
			break;

		case "01001": //SUB RD, R1, R2
			instruction = generateSub(instruccion);
			break;

		case "01010": //OR RD, R1, R2
			instruction = generateOr(instruccion);
			break;

		case "01011": //AND RD, R1, R2
			instruction = generateAnd(instruccion);
			break;

		case "01100": //XOR RD, R1, R2
			instruction = generateXor(instruccion);
			break;

		case "01101": //CMP R1, R2
			instruction = generateCmp(instruccion);
			break;

		case "10000": //NOT RDs
			instruction = generateNot(instruccion);
			break;

		case "10001": //INC RDs
			instruction = generateInc(instruccion);
			break;

		case "10010": //DEC RDs
			instruction = generateDec(instruccion);
			break;

		case "10011": //NEG RDs
			instruction = generateNeg(instruccion);
			break;

		case "10100": //CLI
			instruction = generateCli(instruccion);
			break;

		case "10101": //STI
			instruction = generateSti(instruccion);
			break;

		case "10110": //INT Inm8
			instruction = generateInt(instruccion);
			break;

		case "10111": //IRET
			instruction = generateIret(instruccion);
			break;

		case "11000": //JMP 000 Inm8
			instruction = generateJmpI8(instruccion);
			break;

		case "11001": //JMP RS
			instruction = generateJmpR(instruccion);
			break;

		case "11010": //CALL 000 Inm8
			instruction = generateCallI8(instruccion);
			break;

		case "11011": //CALL Rd
			instruction = generateCallR(instruccion);
			break;

		case "11100": //RET
			instruction = generateRet(instruccion);
			break;

		case "11110": //BR
			switch(instruccion.substring(5,8)) {
			case "000": // BRC
				instruction = generateBrc(instruccion);
				break;

			case "001": // BRNC
				instruction = generateBrnc(instruccion);
				break;

			case "010": // BRO
				instruction = generateBro(instruccion);
				break;

			case "011": // BRNO
				instruction = generateBrno(instruccion);
				break;

			case "100": // BRZ
				instruction = generateBrz(instruccion);
				break;

			case "101": // BRNZ
				instruction = generateBrnz(instruccion);
				break;

			case "110": // BRS
				instruction = generateBrs(instruccion);
				break;

			case "111": // BRNS
				instruction = generateBrns(instruccion);
				break;

			}

			break;
		}

		return instruction;
	}
	
	private ASTNode generateNop(String instruction) {
		return new Nop(0,0);
	}
	
	private ASTNode generateMov0(String instruction) {
		String registro1 = instruction.substring(5, 8);
		String registro2 = instruction.substring(8,11);

		int r1 = Translate.toDecimalInteger(registro1,true);
		int r2 = Translate.toDecimalInteger(registro2,true);

		return new Mov(0, 0, new Register(0,0, "R"+r1), new Register(0,0,"R"+r2),0);
	}
	
	private ASTNode generateMov1(String instruction) {
		String registro1 = instruction.substring(5, 8);
		String registro2 = instruction.substring(8,11);

		int r1 = Translate.toDecimalInteger(registro1,true);
		int r2 = Translate.toDecimalInteger(registro2,true);

		return new Mov(0, 0, new Register(0,0, "R"+r1), new Register(0,0,"R"+r2),1);
	}
	
	private ASTNode generateMov2(String instruction) {
		String registro1 = instruction.substring(5, 8);
		String registro2 = instruction.substring(8,11);

		int r1 = Translate.toDecimalInteger(registro1,true);
		int r2 = Translate.toDecimalInteger(registro2,true);

		return new Mov(0, 0, new Register(0,0, "R"+r1), new Register(0,0,"R"+r2),2);
	}
	
	private ASTNode generateMovl(String instruction) {
		String registro1 = instruction.substring(5, 8);
		String inm8 = instruction.substring(8,instruction.length());

		int r1 = Translate.toDecimalInteger(registro1,true);
		int i8 = Translate.toDecimalInteger(inm8);

		return new Movl(0, 0, new Register(0,0, "R"+r1), new IntegerLiteral(0,0,i8));
	}
	
	private ASTNode generateMovh(String instruction) {
		String registro1 = instruction.substring(5, 8);
		String inm8 = instruction.substring(8,instruction.length());

		int r1 = Translate.toDecimalInteger(registro1,true);
		int i8 = Translate.toDecimalInteger(inm8);

		return new Movh(0, 0, new Register(0,0, "R"+r1), new IntegerLiteral(0,0,i8));
	}
	
	private ASTNode generatePush(String instruction) {
		String registro1 = instruction.substring(5, 8);
		int r1 = Translate.toDecimalInteger(registro1,true);

		return new Push(0,0,new Register(0,0,"R"+r1));
	}
	
	private ASTNode generatePop(String instruction) {
		String registro1 = instruction.substring(5, 8);
		int r1 = Translate.toDecimalInteger(registro1,true);

		return new Pop(0,0,new Register(0,0,"R"+r1));
	}
	
	private ASTNode generateAdd(String instruction) {
		String registro1 = instruction.substring(5,8);
		String registro2 = instruction.substring(8,11);
		String registro3 = instruction.substring(11,14);

		int r1 = Translate.toDecimalInteger(registro1,true);
		int r2 = Translate.toDecimalInteger(registro2,true);
		int r3 = Translate.toDecimalInteger(registro3,true);

		return new Add(0,0,new Register(0,0,"R"+r1),new Register(0,0,"R"+r2), new Register(0,0,"R"+r3));
	}
	
	private ASTNode generateSub(String instruction) {
		String registro1 = instruction.substring(5,8);
		String registro2 = instruction.substring(8,11);
		String registro3 = instruction.substring(11,14);

		int r1 = Translate.toDecimalInteger(registro1,true);
		int r2 = Translate.toDecimalInteger(registro2,true);
		int r3 = Translate.toDecimalInteger(registro3,true);

		return new Sub(0,0,new Register(0,0,"R"+r1),new Register(0,0,"R"+r2), new Register(0,0,"R"+r3));
	}
	
	private ASTNode generateAnd(String instruction) {
		String registro1 = instruction.substring(5,8);
		String registro2 = instruction.substring(8,11);
		String registro3 = instruction.substring(11,14);

		int r1 = Translate.toDecimalInteger(registro1,true);
		int r2 = Translate.toDecimalInteger(registro2,true);
		int r3 = Translate.toDecimalInteger(registro3,true);

		return new And(0,0,new Register(0,0,"R"+r1),new Register(0,0,"R"+r2), new Register(0,0,"R"+r3));
	}
	
	private ASTNode generateOr(String instruction) {
		String registro1 = instruction.substring(5,8);
		String registro2 = instruction.substring(8,11);
		String registro3 = instruction.substring(11,14);

		int r1 = Translate.toDecimalInteger(registro1,true);
		int r2 = Translate.toDecimalInteger(registro2,true);
		int r3 = Translate.toDecimalInteger(registro3,true);

		return new Or(0,0,new Register(0,0,"R"+r1),new Register(0,0,"R"+r2), new Register(0,0,"R"+r3));
	}
	
	private ASTNode generateXor(String instruction) {
		String registro1 = instruction.substring(5,8);
		String registro2 = instruction.substring(8,11);
		String registro3 = instruction.substring(11,14);

		int r1 = Translate.toDecimalInteger(registro1,true);
		int r2 = Translate.toDecimalInteger(registro2,true);
		int r3 = Translate.toDecimalInteger(registro3,true);

		return new Xor(0,0,new Register(0,0,"R"+r1),new Register(0,0,"R"+r2), new Register(0,0,"R"+r3));
	}
	
	private ASTNode generateCmp(String instruction) {
		String registro1 = instruction.substring(5,8);
		String registro2 = instruction.substring(8,11);

		int r1 = Translate.toDecimalInteger(registro1,true);
		int r2 = Translate.toDecimalInteger(registro2,true);

		return new Cmp(0,0,new Register(0,0,"R"+r1),new Register(0,0,"R"+r2));
	}
	
	private ASTNode generateNot(String instruction) {
		String registro1 = instruction.substring(5,8);
		int r1 = Translate.toDecimalInteger(registro1,true);

		return new Not(0,0,new Register(0,0,"R"+r1));
	}
	
	private ASTNode generateInc(String instruction) {
		String registro1 = instruction.substring(5,8);
		int r1 = Translate.toDecimalInteger(registro1,true);

		return new Inc(0,0,new Register(0,0,"R"+r1));
	}
	
	private ASTNode generateDec(String instruction) {
		String registro1 = instruction.substring(5,8);
		int r1 = Translate.toDecimalInteger(registro1,true);

		return new Dec(0,0,new Register(0,0,"R"+r1));
	}
	
	private ASTNode generateNeg(String instruction) {
		String registro1 = instruction.substring(5,8);
		int r1 = Translate.toDecimalInteger(registro1,true);

		return new Neg(0,0,new Register(0,0,"R"+r1));
	}
	
	private ASTNode generateCli(String instruction) {
		return new Cli(0,0);
	}
	
	private ASTNode generateSti(String instruction) {
		return new Sti(0,0);
	}
	
	private ASTNode generateInt(String instruction) {
		String i8 = instruction.substring(8);
		int inm8 = Translate.toDecimalInteger(i8);

		return new Int(0,0,new IntegerLiteral(0,0,inm8));
	}
	
	private ASTNode generateIret(String instruction) {
		return new Iret(0,0);
	}
	
	private ASTNode generateJmpI8(String instruction) {
		String inm8 = instruction.substring(8);
		int i8 = Translate.toDecimalInteger(inm8);

		return new Jmp(0,0,new IntegerLiteral(0,0,i8),0);
	}
	
	private ASTNode generateJmpR(String instruction) {
		String registro1 = instruction.substring(5, 8);
		int r1 = Translate.toDecimalInteger(registro1,true);

		return new Jmp(0,0,new Register(0,0,"R"+r1),1);
	}
	
	private ASTNode generateCallI8(String instruction) {
		String inm8 = instruction.substring(8);
		int i8 = Translate.toDecimalInteger(inm8);

		return new Call(0,0,new IntegerLiteral(0,0,i8),0);
	}
	
	private ASTNode generateCallR(String instruction) {
		String registro1 = instruction.substring(5, 8);
		int r1 = Translate.toDecimalInteger(registro1,true);

		return new Call(0,0,new Register(0,0,"R"+r1),1);
	}
	
	private ASTNode generateRet(String instruction) {
		return new Ret(0,0);
	}
	
	private ASTNode generateBrc(String instruction) {
		String registro1 = instruction.substring(8);
		int r1 = Translate.toDecimalInteger(registro1,true);
		
		return new Brc(0,0,new IntegerLiteral(0,0,r1));
	}
	
	private ASTNode generateBrnc(String instruction) {
		String registro1 = instruction.substring(8);
		int r1 = Translate.toDecimalInteger(registro1,true);
		
		return new Brnc(0,0,new IntegerLiteral(0,0,r1));
	}
	
	private ASTNode generateBro(String instruction) {
		String registro1 = instruction.substring(8);
		int r1 = Translate.toDecimalInteger(registro1,true);
		
		return new Bro(0,0,new IntegerLiteral(0,0,r1));
	}
	
	private ASTNode generateBrno(String instruction) {
		String registro1 = instruction.substring(8);
		int r1 = Translate.toDecimalInteger(registro1,true);
		
		return new Brno(0,0,new IntegerLiteral(0,0,r1));
	}
	
	private ASTNode generateBrz(String instruction) {
		String registro1 = instruction.substring(8);
		int r1 = Translate.toDecimalInteger(registro1,true);
		
		return new Brz(0,0,new IntegerLiteral(0,0,r1));
	}
	
	private ASTNode generateBrnz(String instruction) {
		String registro1 = instruction.substring(8);
		int r1 = Translate.toDecimalInteger(registro1,true);
		
		return new Brnz(0,0,new IntegerLiteral(0,0,r1));
	}
	
	private ASTNode generateBrs(String instruction) {
		String registro1 = instruction.substring(8);
		int r1 = Translate.toDecimalInteger(registro1,true);
		
		return new Brs(0,0,new IntegerLiteral(0,0,r1));
	}
	
	private ASTNode generateBrns(String instruction) {
		String registro1 = instruction.substring(8);
		int r1 = Translate.toDecimalInteger(registro1,true);
		
		return new Brns(0,0,new IntegerLiteral(0,0,r1));
	}
	
}
