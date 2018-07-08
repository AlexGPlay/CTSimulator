package yiplay.language;

import java.io.StringReader;

import yiplay.language.errorManagement.ErrorManager;
import yiplay.language.lexicon.Lexicon;
import yiplay.language.semantic.CheckerVisitor;
import yiplay.language.semantic.SubstitutionVisitor;
import yiplay.language.syntactic.Parser;
import yiplay.language.visitor.Visitor;

public class Compiler {

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
	
}
