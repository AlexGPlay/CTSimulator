package yiplay.language.lexicon;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class InstructionTest {
	
	public List<Integer> analyzeText(String text) {
		List<Integer> values = new ArrayList<Integer>();
		
		text = text.toUpperCase();
		StringReader reader = new StringReader(text);
		
		Lexicon lexicon = new Lexicon(reader);
		
		try {
			int token;
			
			while ((token=lexicon.yylex())!=0) {
				values.add((Integer)token);
			}
		} catch (IOException e) {
			return null;
		}
		
		return values;
	}
	
	public void instructionAssert(String instruction, int token) {
		String[] code = {instruction.toUpperCase(), instruction.toLowerCase(), instruction.toUpperCase() + "\n"};
		
		for(String temp : code) {
			int result = analyzeText(temp).get(0);
			assertEquals(token,result);
		}
		
	}
	
	@Test
	public void instructionTest() {
		instructionAssert("nop", TokenInfo.NOP);
		instructionAssert("mov", TokenInfo.MOV);
		instructionAssert("movl", TokenInfo.MOVL);
		instructionAssert("movh", TokenInfo.MOVH);
		instructionAssert("push", TokenInfo.PUSH);
		instructionAssert("pop", TokenInfo.POP);
		instructionAssert("add", TokenInfo.ADD);
		instructionAssert("sub", TokenInfo.SUB);
		instructionAssert("or", TokenInfo.OR);
		instructionAssert("and", TokenInfo.AND);
		instructionAssert("xor", TokenInfo.XOR);
		instructionAssert("cmp", TokenInfo.CMP);
		instructionAssert("not", TokenInfo.NOT);
		instructionAssert("inc", TokenInfo.INC);
		instructionAssert("dec", TokenInfo.DEC);
		instructionAssert("neg", TokenInfo.NEG);
		instructionAssert("cli", TokenInfo.CLI);
		instructionAssert("sti", TokenInfo.STI);
		instructionAssert("int", TokenInfo.INT);
		instructionAssert("iret", TokenInfo.IRET);
		instructionAssert("jmp", TokenInfo.JMP);
		instructionAssert("call", TokenInfo.CALL);
		instructionAssert("ret", TokenInfo.RET);
		instructionAssert("brc", TokenInfo.BRC);
		instructionAssert("brnc", TokenInfo.BRNC);
		instructionAssert("bro", TokenInfo.BRO);
		instructionAssert("brno", TokenInfo.BRNO);
		instructionAssert("brz", TokenInfo.BRZ);
		instructionAssert("brnz", TokenInfo.BRNZ);
		instructionAssert("brs", TokenInfo.BRS);
		instructionAssert("brns", TokenInfo.BRNS);
		instructionAssert("halt", TokenInfo.HALT);
	}
	
}
