package yiplay.language.semantic;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import yiplay.language.errorManagement.ErrorManager;
import yiplay.language.lexicon.Lexicon;
import yiplay.language.syntactic.Parser;
import yiplay.language.visitor.Visitor;

public class SubstituionTest {

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

		return 0;
	}
	
	@Test
	public void testFail() {
		String code = "jmp SUMA";
		compile(code);
		assertEquals(1, ErrorManager.getManager().getErrors().size());
		assertEquals("Label", ErrorManager.getManager().getErrors().get(0).getErrorType());
		
		code = "test: \r\n" +
				"jmp test2";
		compile(code);
		assertEquals(1, ErrorManager.getManager().getErrors().size());
		assertEquals("Label", ErrorManager.getManager().getErrors().get(0).getErrorType());
	
		code = "test: \r\n" +
		"jmp test2 \r\n" +
		"jmp test2 \r\n" + 
		"call test2 \r\n";
		
		compile(code);
		assertEquals(3, ErrorManager.getManager().getErrors().size());
		assertEquals("Label", ErrorManager.getManager().getErrors().get(0).getErrorType());
		assertEquals("Label", ErrorManager.getManager().getErrors().get(1).getErrorType());
		assertEquals("Label", ErrorManager.getManager().getErrors().get(2).getErrorType());
	}
	
	@Test
	public void testNoFail() {
		String code = "SUMA: \n"
				+ "jmp SUMA";
		compile(code);
		
		assertEquals(0, ErrorManager.getManager().getErrors().size());
		
		code = "jmp SUMA \n" +
				"SUMA:";
		compile(code);
		
		assertEquals(0, ErrorManager.getManager().getErrors().size());
	}

}
