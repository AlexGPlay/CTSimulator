package yiplay.language.lexicon;

import yiplay.language.syntactic.Parser;

public abstract class TokenInfo {

	public static final short NUMERO_ENTERO = Parser.NUMERO_ENTERO;
	public static final short NUMERO_HEXADECIMAL = Parser.NUMERO_HEXADECIMAL;
	public static final short NUMERO_BINARIO = Parser.NUMERO_BINARIO;
	public static final short STRING = Parser.STRING;
	public static final short LABEL = Parser.LABEL;
	public static final short REGISTRO = Parser.REGISTRO;
	
	public static final short NOP = Parser.NOP;
	public static final short MOV = Parser.MOV;
	public static final short MOVL = Parser.MOVL;
	public static final short MOVH = Parser.MOVH;
	public static final short PUSH = Parser.PUSH;
	public static final short POP = Parser.POP;
	public static final short ADD = Parser.ADD;
	public static final short SUB = Parser.SUB;
	public static final short OR = Parser.OR;
	public static final short AND = Parser.AND;
	public static final short XOR = Parser.XOR;
	public static final short CMP = Parser.CMP;
	public static final short NOT = Parser.NOT;
	public static final short INC = Parser.INC;
	public static final short DEC = Parser.DEC;
	public static final short NEG = Parser.NEG;
	public static final short CLI = Parser.CLI;
	public static final short STI = Parser.STI;
	public static final short INT = Parser.INT;
	public static final short IRET = Parser.INT;
	public static final short JMP = Parser.JMP;
	public static final short CALL = Parser.CALL;
	public static final short RET = Parser.RET;
	public static final short BRC = Parser.BRC;
	public static final short BRNC = Parser.BRNC;
	public static final short BRO = Parser.BRO;
	public static final short BRNO = Parser.BRNO;
	public static final short BRZ = Parser.BRZ;
	public static final short BRNZ = Parser.BRNZ;
	public static final short BRS = Parser.BRS;
	public static final short BRNS = Parser.BRNS;
	
}
