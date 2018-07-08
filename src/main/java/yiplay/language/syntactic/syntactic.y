%{
import yiplay.language.lexicon.*;
import yiplay.language.ast.*;
import yiplay.language.ast.statement.*;
import yiplay.language.ast.expression.*;
import yiplay.language.errorManagement.*;
import java.util.ArrayList;
import java.io.Reader;
%}

// * Operators


%%

// * Grammar
program: statements																						{ this.ast = new Program(lexicon.getLine(), lexicon.getColumn(), (ArrayList<Statement>)$1); }
	   ;

statements: 																							{ $$ = new ArrayList<Statement>(); }
		 | statements statement																			{ ((ArrayList<Statement>)$1).add((Statement)$2); $$ = $1; }
		 ;
		 
statement: instructions																					{ $$ = $1; }
		| label																							{ $$ = $1; } 
		;
		
label: 'STRING' ':'																					{ $$ = new Label(lexicon.getLine(), lexicon.getColumn(), (String)$1); }
	 ;
	 
instructions: 'NOP'																						{ $$ = new Nop(lexicon.getLine(), lexicon.getColumn()); }
		    | 'MOV' registro ',' registro																{ $$ = new Mov(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, (Expression)$4, 0); }
		    | 'MOV' registro ',' '[' registro ']'														{ $$ = new Mov(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, (Expression)$5, 1); }
		    | 'MOV' '[' registro ']' ',' registro														{ $$ = new Mov(lexicon.getLine(), lexicon.getColumn(), (Expression)$3, (Expression)$6, 2); }
		    | 'MOVL' registro ',' numeros																{ $$ = new Movl(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, (Expression)$4); }
		    | 'MOVH' registro ',' numeros																{ $$ = new Movh(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, (Expression)$4); }
		    | 'PUSH' registro																			{ $$ = new Push(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'POP' registro																			{ $$ = new Pop(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'ADD' registro ',' registro ',' registro													{ $$ = new Add(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, (Expression)$4, (Expression)$6); }
		    | 'SUB' registro ',' registro ',' registro													{ $$ = new Sub(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, (Expression)$4, (Expression)$6); }
		    | 'OR' registro ',' registro ',' registro													{ $$ = new Or(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, (Expression)$4, (Expression)$6); }
		    | 'AND' registro ',' registro ',' registro													{ $$ = new And(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, (Expression)$4, (Expression)$6); }
		    | 'XOR' registro ',' registro ',' registro													{ $$ = new Xor(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, (Expression)$4, (Expression)$6); }
		    | 'CMP' registro ',' registro																{ $$ = new Cmp(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, (Expression)$4); }
		    | 'NOT' registro																			{ $$ = new Not(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'INC' registro																			{ $$ = new Inc(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'DEC' registro																			{ $$ = new Dec(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'NEG' registro																			{ $$ = new Neg(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'CLI'																						{ $$ = new Cli(lexicon.getLine(), lexicon.getColumn()); }
		    | 'STI'																						{ $$ = new Sti(lexicon.getLine(), lexicon.getColumn()); }
		    | 'INT' numeros																				{ $$ = new Int(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'IRET'																					{ $$ = new Iret(lexicon.getLine(), lexicon.getColumn()); }
		    | 'JMP' numeros																				{ $$ = new Jmp(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, 0); }
	   	    | 'JMP' 'STRING'																			{ $$ = new Jmp(lexicon.getLine(), lexicon.getColumn(), (String)$2); }
		    | 'JMP' registro																			{ $$ = new Jmp(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, 1); }
		    | 'CALL' numeros																			{ $$ = new Call(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, 0); }
		    | 'CALL' 'STRING'																			{ $$ = new Call(lexicon.getLine(), lexicon.getColumn(), (String)$2); }
		    | 'CALL' registro																			{ $$ = new Call(lexicon.getLine(), lexicon.getColumn(), (Expression)$2, 1); }
		    | 'RET'																						{ $$ = new Ret(lexicon.getLine(), lexicon.getColumn()); }
		    | 'BRC' numeros																				{ $$ = new Brc(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'BRC' 'STRING'																			{ $$ = new Brc(lexicon.getLine(), lexicon.getColumn(), (String)$2); }
		    | 'BRNC' numeros																			{ $$ = new Brnc(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'BRNC' 'STRING'																			{ $$ = new Brnc(lexicon.getLine(), lexicon.getColumn(), (String)$2); }
		    | 'BRO' numeros																				{ $$ = new Bro(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'BRO' 'STRING'																			{ $$ = new Bro(lexicon.getLine(), lexicon.getColumn(), (String)$2); }
		    | 'BRNO' numeros																			{ $$ = new Brno(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'BRNO' 'STRING'																			{ $$ = new Brno(lexicon.getLine(), lexicon.getColumn(), (String)$2); }
		    | 'BRZ' numeros																				{ $$ = new Brz(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'BRZ' 'STRING'																			{ $$ = new Brz(lexicon.getLine(), lexicon.getColumn(), (String)$2); }
		    | 'BRNZ' numeros																			{ $$ = new Brnz(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'BRNZ' 'STRING'																			{ $$ = new Brnz(lexicon.getLine(), lexicon.getColumn(), (String)$2); }
		    | 'BRS' numeros																				{ $$ = new Brs(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'BRS' 'STRING'																			{ $$ = new Brs(lexicon.getLine(), lexicon.getColumn(), (String)$2); }
		    | 'BRNS' numeros																			{ $$ = new Brns(lexicon.getLine(), lexicon.getColumn(), (Expression)$2); }
		    | 'BRNS' 'STRING'																			{ $$ = new Brns(lexicon.getLine(), lexicon.getColumn(), (String)$2); }
		    ;
		   
registro: 'REGISTRO'																					{ $$ = new Register(lexicon.getLine(), lexicon.getColumn(), (String)$1); }													

numeros: 'NUMERO_ENTERO'																				{ $$ = new IntegerLiteral(lexicon.getLine(), lexicon.getColumn(), (Integer)$1); }
	   | 'NUMERO_HEXADECIMAL'																			{ $$ = new HexLiteral(lexicon.getLine(), lexicon.getColumn(), (String)$1); }
	   | 'NUMERO_BINARIO'																				{ $$ = new BinaryLiteral(lexicon.getLine(), lexicon.getColumn(), (String)$1); }

%%

private Lexicon lexicon;
private ASTNode ast;

private int yylex () {
    int token=0;
    try { 
		token=lexicon.yylex(); 	
		this.yylval = lexicon.getYylval();
    } catch(Throwable e) {
    	ErrorManager.getManager().addError(ErrorManager.LEXICAL, lexicon.getLine(), lexicon.getColumn(), (String)yylval);
    }
    return token;
}

public void yyerror (String error) {
	ErrorManager.getManager().addError(ErrorManager.SYNTACTIC, lexicon.getLine(), lexicon.getColumn(), (String)lexicon.getYylval());
}

public Parser(Lexicon lexicon) {
	this.lexicon = lexicon;
}

public ASTNode getAST(){
	return this.ast;
}
