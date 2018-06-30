package yiplay;

import java.io.FileReader;
import java.io.IOException;

import yiplay.language.syntactic.*;
import yiplay.language.lexicon.*;

public class MainClass {

	public static void main(String args[]) throws IOException {
		if (args.length<1) {
			System.err.println("Need input file.");
			return;
		}

		FileReader fr=null;
		try {
			fr=new FileReader(args[0]);
		} catch(IOException io) {
			System.err.println("Error opening "+args[0]);
			return;
		}

		Lexicon lexico = new Lexicon(fr);
		Parser parser = new Parser(lexico);
		int token;
		while ((token=lexico.yylex())!=0) {
			System.out.println("Line: "+lexico.getLine()+
					", Column: "+lexico.getColumn()+
					", Token: "+token+
					", Value: "+lexico.getYylval()+".");
		}
	}
}