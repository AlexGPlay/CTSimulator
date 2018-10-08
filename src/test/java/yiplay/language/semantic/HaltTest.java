package yiplay.language.semantic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.StringReader;

import org.junit.Test;

import yiplay.language.ast.Program;
import yiplay.language.ast.statement.Halt;
import yiplay.language.errorManagement.ErrorManager;
import yiplay.language.lexicon.Lexicon;
import yiplay.language.syntactic.Parser;
import yiplay.language.visitor.Visitor;

public class HaltTest {

	public Program compile(String code) {
		ErrorManager.getManager().reset();
		
		code = code.toUpperCase();
		StringReader reader = new StringReader(code);

		Lexicon lexico = new Lexicon(reader);
		Parser parser = new Parser(lexico);
		parser.run();
		
		if(ErrorManager.getManager().hasErrors())
			return null;
		
		Visitor labelVisitor = new SubstitutionVisitor();
		parser.getAST().accept(labelVisitor, null);
		
		Visitor lengthVisitor = new CheckerVisitor();
		parser.getAST().accept(lengthVisitor, null);
		
		Visitor haltVisitor = new HaltVisitor();
		parser.getAST().accept(haltVisitor, null);

		return (Program) parser.getAST();
	}
	
	@Test
	public void testNoHalt() {
		String code = "movl r0, 1h";
		Program program = compile(code);
		
		if(program == null)
			fail("Program is null");
		
		assertEquals(program.getStatements().size(), 2);
		assertEquals(program.getStatements().get(program.getStatements().size()-1), new Halt(0,0));
	}
	
	@Test
	public void testHalt() {
		String code = "movl r0, 1h\n"
				+ "halt";
		Program program = compile(code);
		
		if(program == null)
			fail("Program is null");
		
		assertEquals(program.getStatements().size(), 2);
		assertNotEquals(program.getStatements().get(program.getStatements().size()-1), new Halt(0,0));
	
	}
	
}
