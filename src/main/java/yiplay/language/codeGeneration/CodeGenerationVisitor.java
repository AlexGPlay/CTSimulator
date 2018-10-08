package yiplay.language.codeGeneration;

import java.util.ArrayList;
import java.util.List;

import yiplay.language.ast.statement.Add;
import yiplay.language.ast.statement.And;
import yiplay.language.ast.statement.Brc;
import yiplay.language.ast.statement.Brnc;
import yiplay.language.ast.statement.Brno;
import yiplay.language.ast.statement.Brns;
import yiplay.language.ast.statement.Brnz;
import yiplay.language.ast.statement.Bro;
import yiplay.language.ast.statement.Brs;
import yiplay.language.ast.statement.Brz;
import yiplay.language.ast.statement.Call;
import yiplay.language.ast.statement.Cli;
import yiplay.language.ast.statement.Cmp;
import yiplay.language.ast.statement.Dec;
import yiplay.language.ast.statement.Halt;
import yiplay.language.ast.statement.Inc;
import yiplay.language.ast.statement.Int;
import yiplay.language.ast.statement.Iret;
import yiplay.language.ast.statement.Jmp;
import yiplay.language.ast.statement.Mov;
import yiplay.language.ast.statement.Movh;
import yiplay.language.ast.statement.Movl;
import yiplay.language.ast.statement.Neg;
import yiplay.language.ast.statement.Nop;
import yiplay.language.ast.statement.Not;
import yiplay.language.ast.statement.Or;
import yiplay.language.ast.statement.Pop;
import yiplay.language.ast.statement.Push;
import yiplay.language.ast.statement.Ret;
import yiplay.language.ast.statement.Sti;
import yiplay.language.ast.statement.Sub;
import yiplay.language.ast.statement.Xor;
import yiplay.language.visitor.AbstractVisitor;

public class CodeGenerationVisitor extends AbstractVisitor{

	public final static String NOP = "0000000000000000";
	public final static String HALT = "1111111111111111";
	public final static String MOV_RD_RS = "00001%s%s00000"; // 3 bits + 3 bits
	public final static String MOV_RD_RI = "00010%s%s00000"; // 3 bits + 3 bits
	public final static String MOV_RI_RS = "00011%s%s00000"; // 3 bits + 3 bits
	public final static String MOVL = "00100%s%s"; // 3 bits + 8 bits
	public final static String MOVH = "00101%s%s"; // 3 bits + 8 bits
	public final static String PUSH = "00110%s00000000"; // 3 bits
	public final static String POP = "00111%s00000000"; // 3 bits
	public final static String ADD = "01000%s%s%s00"; // 3 bits + 3 bits + 3 bits
	public final static String SUB = "01001%s%s%s00"; // 3 bits + 3 bits + 3 bits
	public final static String OR = "01010%s%s%s00"; // 3 bits + 3 bits + 3 bits
	public final static String AND = "01011%s%s%s00"; // 3 bits + 3 bits + 3 bits
	public final static String XOR = "01100%s%s%s00"; // 3 bits + 3 bits + 3 bits
	public final static String CMP = "01101%s%s00000"; // 3 bits + 3 bits
	public final static String NOT = "10000%s00000000"; // 3 bits
	public final static String INC = "10001%s00000000"; // 3 bits
	public final static String DEC = "10010%s00000000"; // 3 bits
	public final static String NEG = "10011%s00000000"; // 3 bits
	public final static String CLI = "1010000000000000";
	public final static String STI = "1010100000000000";
	public final static String INT = "10110000%s"; // 8 bits
	public final static String IRET = "1011100000000000";
	public final static String JMP8 = "11000000%s"; // 8 bits
	public final static String JMPR = "11001%s00000000"; // 3 bits
	public final static String CALL8 = "11010000%s"; // 8 bits
	public final static String CALLR = "11011%s00000000"; // 3 bits	
	public final static String RET = "1110000000000000";
	public final static String BRC = "11110000%s"; // 8 bits
	public final static String BRNC = "11110001%s"; // 8 bits
	public final static String BRO = "11110010%s"; // 8 bits
	public final static String BRNO = "11110011%s"; // 8 bits
	public final static String BRZ = "11110100%s"; // 8 bits
	public final static String BRNZ = "11110101%s"; // 8 bits
	public final static String BRS = "11110110%s"; // 8 bits
	public final static String BRNS = "11110111%s"; // 8 bits
	
	private List<String> instructions;
	
	public CodeGenerationVisitor() {
		instructions = new ArrayList<String>();
	}
	
	public List<String> getInstructions(){
		return instructions;
	}
	
	public Object visit(Add ast, Object param) {
		String rs1 = ast.getRs1().translate();
		String rs2 = ast.getRs2().translate();
		String rd = ast.getRd().translate();
		
		String instruction = String.format(ADD, rd, rs1, rs2);
		instructions.add(instruction);
		
		return null;
	}
	
	public Object visit(And ast, Object param) {
		String rs1 = ast.getRs1().translate();
		String rs2 = ast.getRs2().translate();
		String rd = ast.getRd().translate();
		
		String instruction = String.format(AND, rd, rs1, rs2);
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Brc ast, Object param) {
		String inm8 = ast.getLines().translate();
		
		String instruction = String.format(BRC, inm8);
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Brnc ast, Object param) {
		String inm8 = ast.getLines().translate();
		
		String instruction = String.format(BRNC, inm8);
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Brno ast, Object param) {
		String inm8 = ast.getLines().translate();
		
		String instruction = String.format(BRNO, inm8);
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Brns ast, Object param) {
		String inm8 = ast.getLines().translate();
		
		String instruction = String.format(BRNS, inm8);
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Brnz ast, Object param) {
		String inm8 = ast.getLines().translate();
		
		String instruction = String.format(BRNZ, inm8);
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Bro ast, Object param) {
		String inm8 = ast.getLines().translate();
		
		String instruction = String.format(BRO, inm8);
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Brs ast, Object param) {
		String inm8 = ast.getLines().translate();
		
		String instruction = String.format(BRS, inm8);
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Brz ast, Object param) {
		String inm8 = ast.getLines().translate();
		
		String instruction = String.format(BRZ, inm8);
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Call ast, Object param) {
		String instruction = null;
		
		if(ast.getMode() == 0) {
			String lines = ast.getLines().translate();
			instruction = String.format(CALL8, lines);
		}
		
		else if(ast.getMode() == 1) {
			String register = ast.getLines().translate();
			instruction = String.format(CALLR, register);
		}
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Cli ast, Object param) {
		String instruction = String.format(CLI);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Cmp ast, Object param) {
		String rs1 = ast.getRs1().translate();
		String rs2 = ast.getRs2().translate();
		
		String instruction = String.format(CMP, rs1, rs2);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Dec ast, Object param) {
		String rsd = ast.getRsd().translate();
		
		String instruction = String.format(DEC, rsd);
		
		instructions.add(instruction);
		
		return null;
	}
	
	public Object visit(Halt halt, Object param) {
		String instruction = String.format(HALT);
		instructions.add(instruction);
		return null;
	}

	public Object visit(Inc ast, Object param) {
		String rsd = ast.getRsd().translate();
		
		String instruction = String.format(INC, rsd);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Int ast, Object param) {
		String interruption = ast.getInterruption().translate();
		
		String instruction = String.format(INT, interruption);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Iret ast, Object param) {
		String instruction = String.format(IRET);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Jmp ast, Object param) {
		String instruction = null;
		
		if(ast.getMode() == 0) {
			String lines = ast.getLines().translate();
			instruction = String.format(JMP8, lines);
		}
		
		else if(ast.getMode() == 1) {
			String register = ast.getLines().translate();
			instruction = String.format(JMPR, register);
		}
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Mov ast, Object param) {
		String rd = ast.getRd().translate();
		String rs = ast.getRs().translate();
		
		String instruction = null;
		
		if(ast.getType() == 0) 
			instruction = String.format(MOV_RD_RS, rd, rs);
		
		else if(ast.getType() == 1) 
			instruction = String.format(MOV_RD_RI, rd, rs);
		
		else if(ast.getType() == 2) 
			instruction = String.format(MOV_RI_RS, rd, rs);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Movh ast, Object param) {
		String number = ast.getNumber().translate();
		String register = ast.getRegister().translate();
		
		String instruction = String.format(MOVH, register, number);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Movl ast, Object param) {
		String number = ast.getNumber().translate();
		String register = ast.getRegister().translate();
		
		String instruction = String.format(MOVL, register, number);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Neg ast, Object param) {
		String rsd = ast.getRsd().translate();
		
		String instruction = String.format(NEG, rsd);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Nop ast, Object param) {
		String instruction = String.format(NOP);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Not ast, Object param) {
		String rsd = ast.getRsd().translate();
		
		String instruction = String.format(NOT, rsd);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Or ast, Object param) {
		String rs1 = ast.getRs1().translate();
		String rs2 = ast.getRs2().translate();
		String rd = ast.getRd().translate();
		
		String instruction = String.format(OR, rd, rs1, rs2);
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Pop ast, Object param) {
		String register = ast.getRegister().translate();
		
		String instruction = String.format(POP, register);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Push ast, Object param) {
		String register = ast.getRegister().translate();
		
		String instruction = String.format(PUSH, register);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Ret ast, Object param) {
		String instruction = String.format(RET);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Sti ast, Object param) {
		String instruction = String.format(STI);
		
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Sub ast, Object param) {
		String rs1 = ast.getRs1().translate();
		String rs2 = ast.getRs2().translate();
		String rd = ast.getRd().translate();
		
		String instruction = String.format(SUB, rd, rs1, rs2);
		instructions.add(instruction);
		
		return null;
	}

	public Object visit(Xor ast, Object param) {
		String rs1 = ast.getRs1().translate();
		String rs2 = ast.getRs2().translate();
		String rd = ast.getRd().translate();
		
		String instruction = String.format(XOR, rd, rs1, rs2);
		instructions.add(instruction);
		
		return null;
	}
	
}
