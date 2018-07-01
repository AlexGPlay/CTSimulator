//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package yiplay.language.syntactic;



//#line 2 "../../src/main/java/yiplay/language/syntactic/syntactic.y"
import yiplay.language.lexicon.*;
import java.io.Reader;
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
final Object dup_yyval(Object val)
{
  return val;
}
//#### end semantic value section ####
public final static short STRING=257;
public final static short LABEL=258;
public final static short NOP=259;
public final static short MOV=260;
public final static short MOVL=261;
public final static short MOVH=262;
public final static short PUSH=263;
public final static short POP=264;
public final static short ADD=265;
public final static short SUB=266;
public final static short OR=267;
public final static short AND=268;
public final static short XOR=269;
public final static short CMP=270;
public final static short NOT=271;
public final static short INC=272;
public final static short DEC=273;
public final static short NEG=274;
public final static short CLI=275;
public final static short STI=276;
public final static short INT=277;
public final static short IRET=278;
public final static short JMP=279;
public final static short CALL=280;
public final static short RET=281;
public final static short BRC=282;
public final static short BRNC=283;
public final static short BRO=284;
public final static short BRNO=285;
public final static short BRZ=286;
public final static short BRNZ=287;
public final static short BRS=288;
public final static short BRNS=289;
public final static short REGISTRO=290;
public final static short NUMERO_ENTERO=291;
public final static short NUMERO_HEXADECIMAL=292;
public final static short NUMERO_BINARIO=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    4,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    5,    6,    6,    6,
};
final static short yylen[] = {                            2,
    1,    0,    2,    1,    1,    2,    1,    4,    6,    6,
    4,    4,    2,    2,    6,    6,    6,    6,    6,    4,
    2,    2,    2,    2,    1,    1,    2,    1,    2,    2,
    2,    2,    2,    2,    1,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    1,    1,    1,    1,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    7,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   25,
   26,    0,   28,    0,    0,   35,    0,    0,    0,    0,
    0,    0,    0,    0,    3,    4,    5,    6,    0,   52,
    0,    0,    0,   13,   14,    0,    0,    0,    0,    0,
    0,   21,   22,   23,   24,   53,   54,   55,   27,   30,
   31,   29,   33,   34,   32,   37,   36,   39,   38,   41,
   40,   43,   42,   45,   44,   47,   46,   49,   48,   51,
   50,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    8,   11,   12,    0,    0,    0,    0,
    0,   20,    0,    0,    0,    0,    0,    0,    0,   10,
    9,   15,   16,   17,   18,   19,
};
final static short yydgoto[] = {                          1,
    2,   35,   36,   37,   41,   59,
};
final static short yysindex[] = {                         0,
    0, -132, -234,    0,  -91, -267, -267, -267, -267, -267,
 -267, -267, -267, -267, -267, -267, -267, -267, -267,    0,
    0, -274,    0, -241, -235,    0, -232, -229, -226, -185,
 -182, -179, -176, -169,    0,    0,    0,    0, -267,    0,
  -18,  -17,  -15,    0,    0,  -14,  -12,    1,    2,    3,
    4,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -49,  -90, -274, -274, -267, -267, -267, -267, -267,
 -267,   10, -267,    0,    0,    0,   24,   25,   26,   27,
   29,    0, -267,  -40, -267, -267, -267, -267, -267,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,   74,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,   -4,    9,
};
final static int YYTABLESIZE=200;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         39,
   93,   42,   43,   44,   45,   46,   47,   48,   49,   50,
   51,   52,   53,   54,   55,   60,   56,   57,   58,   61,
   64,   63,   40,   38,   66,   83,   84,   68,   85,   86,
   70,   87,   62,   65,   82,   67,   69,   71,   73,   75,
   77,   79,   81,   92,   88,   89,   90,   91,   40,   56,
   57,   58,  111,  103,   40,   56,   57,   58,   56,   57,
   58,   56,   57,   58,   56,   57,   58,  105,  106,  107,
  108,   72,  109,    1,   74,    0,    0,   76,   94,    0,
   78,   97,   98,   99,  100,  101,  102,   80,  104,    0,
    0,    0,   95,   96,    0,    0,    0,    0,  110,    0,
  112,  113,  114,  115,  116,   56,   57,   58,   56,   57,
   58,   56,   57,   58,   56,   57,   58,    0,    0,    0,
    0,   56,   57,   58,    3,    0,    4,    5,    6,    7,
    8,    9,   10,   11,   12,   13,   14,   15,   16,   17,
   18,   19,   20,   21,   22,   23,   24,   25,   26,   27,
   28,   29,   30,   31,   32,   33,   34,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   40,   40,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         91,
   91,    6,    7,    8,    9,   10,   11,   12,   13,   14,
   15,   16,   17,   18,   19,  257,  291,  292,  293,   24,
   25,  257,  290,  258,  257,   44,   44,  257,   44,   44,
  257,   44,   24,   25,   39,   27,   28,   29,   30,   31,
   32,   33,   34,   93,   44,   44,   44,   44,  290,  291,
  292,  293,   93,   44,  290,  291,  292,  293,  291,  292,
  293,  291,  292,  293,  291,  292,  293,   44,   44,   44,
   44,  257,   44,    0,  257,   -1,   -1,  257,   83,   -1,
  257,   86,   87,   88,   89,   90,   91,  257,   93,   -1,
   -1,   -1,   84,   85,   -1,   -1,   -1,   -1,  103,   -1,
  105,  106,  107,  108,  109,  291,  292,  293,  291,  292,
  293,  291,  292,  293,  291,  292,  293,   -1,   -1,   -1,
   -1,  291,  292,  293,  257,   -1,  259,  260,  261,  262,
  263,  264,  265,  266,  267,  268,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,  286,  287,  288,  289,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  290,  290,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,"','",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,"'['",
null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"\"STRING\"","\"LABEL\"","\"NOP\"","\"MOV\"",
"\"MOVL\"","\"MOVH\"","\"PUSH\"","\"POP\"","\"ADD\"","\"SUB\"","\"OR\"",
"\"AND\"","\"XOR\"","\"CMP\"","\"NOT\"","\"INC\"","\"DEC\"","\"NEG\"","\"CLI\"",
"\"STI\"","\"INT\"","\"IRET\"","\"JMP\"","\"CALL\"","\"RET\"","\"BRC\"",
"\"BRNC\"","\"BRO\"","\"BRNO\"","\"BRZ\"","\"BRNZ\"","\"BRS\"","\"BRNS\"",
"\"REGISTRO\"","\"NUMERO_ENTERO\"","\"NUMERO_HEXADECIMAL\"",
"\"NUMERO_BINARIO\"",
};
final static String yyrule[] = {
"$accept : program",
"program : sentences",
"sentences :",
"sentences : sentences sentence",
"sentence : expression",
"sentence : label",
"label : \"STRING\" \"LABEL\"",
"expression : \"NOP\"",
"expression : \"MOV\" registro ',' registro",
"expression : \"MOV\" registro ',' '[' registro ']'",
"expression : \"MOV\" '[' registro ']' ',' registro",
"expression : \"MOVL\" registro ',' numeros",
"expression : \"MOVH\" registro ',' numeros",
"expression : \"PUSH\" registro",
"expression : \"POP\" registro",
"expression : \"ADD\" registro ',' registro ',' registro",
"expression : \"SUB\" registro ',' registro ',' registro",
"expression : \"OR\" registro ',' registro ',' registro",
"expression : \"AND\" registro ',' registro ',' registro",
"expression : \"XOR\" registro ',' registro ',' registro",
"expression : \"CMP\" registro ',' registro",
"expression : \"NOT\" registro",
"expression : \"INC\" registro",
"expression : \"DEC\" registro",
"expression : \"NEG\" registro",
"expression : \"CLI\"",
"expression : \"STI\"",
"expression : \"INT\" numeros",
"expression : \"IRET\"",
"expression : \"JMP\" numeros",
"expression : \"JMP\" \"STRING\"",
"expression : \"JMP\" registro",
"expression : \"CALL\" numeros",
"expression : \"CALL\" \"STRING\"",
"expression : \"CALL\" registro",
"expression : \"RET\"",
"expression : \"BRC\" numeros",
"expression : \"BRC\" \"STRING\"",
"expression : \"BRNC\" numeros",
"expression : \"BRNC\" \"STRING\"",
"expression : \"BRO\" numeros",
"expression : \"BRO\" \"STRING\"",
"expression : \"BRNO\" numeros",
"expression : \"BRNO\" \"STRING\"",
"expression : \"BRZ\" numeros",
"expression : \"BRZ\" \"STRING\"",
"expression : \"BRNZ\" numeros",
"expression : \"BRNZ\" \"STRING\"",
"expression : \"BRS\" numeros",
"expression : \"BRS\" \"STRING\"",
"expression : \"BRNS\" numeros",
"expression : \"BRNS\" \"STRING\"",
"registro : \"REGISTRO\"",
"numeros : \"NUMERO_ENTERO\"",
"numeros : \"NUMERO_HEXADECIMAL\"",
"numeros : \"NUMERO_BINARIO\"",
};

//#line 80 "../../src/main/java/yiplay/language/syntactic/syntactic.y"

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
//#line 354 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
