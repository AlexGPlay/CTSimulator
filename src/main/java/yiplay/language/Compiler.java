package yiplay.language;

import java.io.IOException;
import java.io.StringReader;

import yiplay.language.lexicon.Lexicon;
import yiplay.language.syntactic.Parser;

public class Compiler {

	public int compile(String code) {
		code = code.toUpperCase();
		StringReader reader = new StringReader(code);

		Lexicon lexico = new Lexicon(reader);
		Parser parser = new Parser(lexico);
		parser.run();
		return 0;
	}
	
}
