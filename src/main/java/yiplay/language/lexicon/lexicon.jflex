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
ConstanteEntera = [0-9]*

%%
// ************  Actions ********************

// * Constante Entera
{ConstanteEntera}	{ 
	this.yylval = new Integer(yytext());
	return 0;  
}
