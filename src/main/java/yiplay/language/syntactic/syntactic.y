%{
import yiplay.language.lexicon.*;
import java.io.Reader;
%}

// * Operators


%%

// * Grammar
program: sentences
	   ;

sentences: 
		 | sentences sentence
		 ;
		 
sentence: expression
		| label
		;
		
label: 'STRING' 'LABEL'
	 ;
	 
expression: 'NOP'
		  | 'MOV' registro ',' registro
		  | 'MOV' registro ',' '[' registro ']'
		  | 'MOV' '[' registro ']' ',' registro
		  | 'MOVL' registro ',' numeros
		  | 'MOVH' registro ',' numeros
		  | 'PUSH' registro
		  | 'POP' registro
		  | 'ADD' registro ',' registro ',' registro
		  | 'SUB' registro ',' registro ',' registro
		  | 'OR' registro ',' registro ',' registro
		  | 'AND' registro ',' registro ',' registro
		  | 'XOR' registro ',' registro ',' registro
		  | 'CMP' registro ',' registro
		  | 'NOT' registro
		  | 'INC' registro
		  | 'DEC' registro
		  | 'NEG' registro
		  | 'CLI'
		  | 'STI'
		  | 'INT' numeros
		  | 'IRET'
		  | 'JMP' numeros
		  | 'JMP' 'STRING'
		  | 'JMP' registro
		  | 'CALL' numeros
		  | 'CALL' 'STRING'
		  | 'CALL' registro
		  | 'RET'
		  | 'BRC' numeros
		  | 'BRC' 'STRING'
		  | 'BRNC' numeros
		  | 'BRNC' 'STRING'
		  | 'BRO' numeros
		  | 'BRO' 'STRING'
		  | 'BRNO' numeros
		  | 'BRNO' 'STRING'
		  | 'BRZ' numeros
		  | 'BRZ' 'STRING'
		  | 'BRNZ' numeros
		  | 'BRNZ' 'STRING'
		  | 'BRS' numeros
		  | 'BRS' 'STRING'
		  | 'BRNS' numeros
		  | 'BRNS' 'STRING'
		  ;
		  
registro: 'REGISTRO'

numeros: 'NUMERO_ENTERO'
	   | 'NUMERO_HEXADECIMAL'
	   | 'NUMERO_BINARIO'

%%

private Lexicon lexicon;

private int yylex () {
    int token=0;
    try { 
		token=lexicon.yylex(); 	
		this.yylval = lexicon.getYylval();
    } catch(Throwable e) {
	    System.err.println ("Lexical Error on " + lexicon.getLine()+
		":"+ lexicon.getColumn()+"\n\t"+e); 
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
