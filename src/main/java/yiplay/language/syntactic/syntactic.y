%{
import yiplay.language.lexicon.*;
import java.io.Reader;
%}

// * Declarations
%token test

// * Operators


%%

// * Grammar
program: sentences
	   ;

%%

private Lexicon lexicon;

private int yylex () {
    int token=0;
    try { 
		token=lexicon.yylex(); 	
		this.yylval = lexicon.getYylval();
    } catch(Throwable e) {
	    System.err.println ("Lexical Error on " + lexicon.getLine()+
		":"+ lexicon.getColumn()+":\n\t"+e); 
    }
    return token;
}

public void yyerror (String error) {
    System.err.println ("Syntactic Error " + lexicon.getYylval() + " on " + lexicon.getLine()+
		":"+lexicon.getColumn()+":\n\t"+error);
}

public Parser(Lexicon lexicon) {
	this.lexicon = lexicon;
}
