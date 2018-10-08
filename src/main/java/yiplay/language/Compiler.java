package yiplay.language;

import java.io.StringReader;
import java.util.List;

import yiplay.language.codeGeneration.CodeGenerationVisitor;
import yiplay.language.errorManagement.ErrorManager;
import yiplay.language.lexicon.Lexicon;
import yiplay.language.semantic.CheckerVisitor;
import yiplay.language.semantic.HaltVisitor;
import yiplay.language.semantic.SubstitutionVisitor;
import yiplay.language.syntactic.Parser;
import yiplay.language.visitor.Visitor;

public class Compiler {

	public List<String> compile(String code) {
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
		
		if(ErrorManager.getManager().hasErrors())
			return null;
		
		CodeGenerationVisitor codeGenerationVisitor = new CodeGenerationVisitor();
		parser.getAST().accept(codeGenerationVisitor, null);

		return codeGenerationVisitor.getInstructions();
	}
	
}
