// ************  Package and imports ********************

package yiplay.language.lexicon;
import yiplay.language.syntactic.*;

%%
// ************  Options ********************
// % debug // * Opción para depurar
%byaccj
%class Lexicon
%public
%unicode
%line
%column

%{
// ************  Atributes and methods ********************

public int getLine() { 
	return yyline+1;
}

public int getColumn() { 
	// * Flex empieza en cero
	return yycolumn+1;
}

private Object yylval;
public Object getYylval() {
	return this.yylval;
}

%}

// ************  Regex ********************

// ----------- CONSTANTES -----------

ConstanteEntera = [0-9]+
ConstanteHexadecimal = [0-9A-F]+"H"|"0H"[0-9A-F]+
ConstanteBinaria = "0B"[0-1]+
Registro = "R"[0-9]+
ConstanteString = [A-Z][A-Z0-9]*
Comentario = ";".*\n
Espacios = [ \n\r\t]
Separador = ","
Label = ":"
CorcheteApertura = "["
CorcheteCierre = "]"

// ----------- PALABRAS RESERVADAS -----------

NOP = "NOP"
MOV = "MOV"
MOVL = "MOVL"
MOVH = "MOVH"
PUSH = "PUSH"
POP = "POP"

ADD = "ADD"
SUB = "SUB"
OR = "OR"
AND = "AND"
XOR = "XOR"

CMP = "CMP"

NOT = "NOT"
INC = "INC"
DEC = "DEC"
NEG = "NEG"

CLI = "CLI"
STI = "STI"
INT = "INT"
IRET = "IRET"

JMP = "JMP"
CALL = "CALL"
RET = "RET"

BRC = "BRC"
BRNC = "BRNC"
BRO = "BRO"
BRNO = "BRNO"
BRZ = "BRZ"
BRNZ = "BRNZ"
BRS = "BRS"
BRNS = "BRNS"

%%
// ************  Actions ********************

// * Numero Entero
{ConstanteEntera}	{ 
	this.yylval = new Integer(yytext());
	return TokenInfo.NUMERO_ENTERO;  
}

// * Numero hexadecimal
{ConstanteHexadecimal}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.NUMERO_HEXADECIMAL;  
}

// * Numero Binario
{ConstanteBinaria}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.NUMERO_BINARIO;  
}

// * Registro
{Registro}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.REGISTRO;  
}

// * Separador
{Separador}	{ 
	this.yylval = new String(yytext());
	return yytext().charAt(0);   
}

// * Label
{Label}	{ 
	this.yylval = new String(yytext());
	return yytext().charAt(0);
}

// * Corchete apertura
{CorcheteApertura}	{ 
	this.yylval = new String(yytext());
	return yytext().charAt(0);   
}

// * Corchete cierre
{CorcheteCierre}	{ 
	this.yylval = new String(yytext());
	return yytext().charAt(0);     
}

// * Comentario
{Comentario}	{ 

}

// * Espacios
{Espacios}	{ 

}

// * Reservada NOP
{NOP}	{ 
	this.yylval = new String(yytext());
	return Parser.NOP;  
}

// * Reservada MOV
{MOV}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.MOV;  
}

// * Reservada MOVL
{MOVL}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.MOVL;  
}

// * Reservada MOVH
{MOVH}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.MOVH;  
}

// * Reservada PUSH
{PUSH}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.PUSH;  
}

// * Reservada POP
{POP}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.POP;  
}

// * Reservada ADD
{ADD}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.ADD;  
}

// * Reservada SUB
{SUB}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.SUB;  
}

// * Reservada OR
{OR}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.OR;  
}

// * Reservada AND
{AND}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.AND;  
}

// * Reservada XOR
{XOR}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.XOR;  
}

// * Reservada CMP
{CMP}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.CMP;  
}

// * Reservada NOT
{NOT}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.NOT;  
}

// * Reservada INC
{INC}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.INC;  
}

// * Reservada DEC
{DEC}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.DEC;  
}

// * Reservada NEG
{NEG}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.NEG;  
}

// * Reservada CLI
{CLI}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.CLI;  
}

// * Reservada STI
{STI}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.STI;  
}

// * Reservada INT
{INT}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.INT;  
}

// * Reservada IRET
{IRET}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.IRET;  
}

// * Reservada JMP
{JMP}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.JMP;  
}

// * Reservada CALL
{CALL}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.CALL;  
}

// * Reservada RET
{RET}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.RET;  
}

// * Reservada BRC
{BRC}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.BRC;  
}

// * Reservada BRNC
{BRNC}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.BRNC;  
}

// * Reservada BRO
{BRO}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.BRO;  
}

// * Reservada BRNO
{BRNO}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.BRNO;  
}

// * Reservada BRZ
{BRZ}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.BRZ;  
}

// * Reservada BRNZ
{BRNZ}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.BRNZ;  
}

// * Reservada BRS
{BRS}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.BRS;  
}

// * Reservada BRNS
{BRNS}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.BRNS	;  
}

// * String
{ConstanteString}	{ 
	this.yylval = new String(yytext());
	return TokenInfo.STRING;  
}