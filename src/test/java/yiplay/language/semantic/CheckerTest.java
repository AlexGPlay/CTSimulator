package yiplay.language.semantic;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import yiplay.language.errorManagement.ErrorManager;
import yiplay.language.lexicon.Lexicon;
import yiplay.language.syntactic.Parser;
import yiplay.language.visitor.Visitor;

public class CheckerTest {

	public int compile(String code) {
		ErrorManager.getManager().reset();
		
		code = code.toUpperCase();
		StringReader reader = new StringReader(code);

		Lexicon lexico = new Lexicon(reader);
		Parser parser = new Parser(lexico);
		parser.run();
		
		if(ErrorManager.getManager().hasErrors())
			return -1;
		
		Visitor labelVisitor = new SubstitutionVisitor();
		parser.getAST().accept(labelVisitor, null);
		
		Visitor lengthVisitor = new CheckerVisitor();
		parser.getAST().accept(lengthVisitor, null);

		return 0;
	}
	
	@Test
	public void testBinaryNoFail() {
		String code = "movl r0, 0b111011";
		compile(code);
		
		assertFalse(ErrorManager.getManager().hasErrors());
	}
	
	@Test
	public void testBinaryFail() {
		String code = "movl r0, 0b000000000";
		compile(code);
		
		assertTrue(ErrorManager.getManager().hasErrors());
		assertEquals("Binary maximum size is 8 bits but is 9 bits long", ErrorManager.getManager().getErrors().get(0).getDesc());
	}
	
	@Test
	public void testHexNoFail() {
		
		String code = "movl r0, 0hFF";
		compile(code);
		
		assertFalse(ErrorManager.getManager().hasErrors());
		
		code = "movl r0, FFh";
		compile(code);
		
		assertFalse(ErrorManager.getManager().hasErrors());
	}
	
	@Test
	public void testHexFail() {
		String code = "movl r0, 0hFFFFF";
		compile(code);
		
		assertTrue(ErrorManager.getManager().hasErrors());
		
		code = "movl r0, FFFFFh";
		compile(code);
		
		assertTrue(ErrorManager.getManager().hasErrors());
	}
	
	@Test
	public void testIntegerNoFail() {
		String code = "movl r0, 1";
		compile(code);
		
		assertFalse(ErrorManager.getManager().hasErrors());
		
		code = "movl r0, 255";
		compile(code);
		
		assertFalse(ErrorManager.getManager().hasErrors());
	}
	
	@Test
	public void testIntegerFail() {
		String code = "movl r0, 256";
		compile(code);
		
		assertTrue(ErrorManager.getManager().hasErrors());
	}
	
	@Test
	public void testRegisterNoFail() {
		String code = "movl r0, 5";
		compile(code);
		
		assertFalse(ErrorManager.getManager().hasErrors());
	}
	
	@Test
	public void testRegisterFail() {
		String code = "movl r8, 5";
		compile(code);
		
		assertTrue(ErrorManager.getManager().hasErrors());
	}

}
