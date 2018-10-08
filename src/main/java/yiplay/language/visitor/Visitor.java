package yiplay.language.visitor;

import yiplay.language.ast.*;
import yiplay.language.ast.expression.*;
import yiplay.language.ast.statement.*;

public interface Visitor {

	Object visit(Program ast, Object param);
	
	Object visit(BinaryLiteral ast, Object param);
	Object visit(HexLiteral ast, Object param);
	Object visit(IntegerLiteral ast, Object param);
	Object visit(Register ast, Object param);
	
	Object visit(Add ast, Object param);
	Object visit(And ast, Object param);
	Object visit(Brc ast, Object param);
	Object visit(Brnc ast, Object param);
	Object visit(Brno ast, Object param);
	Object visit(Brns ast, Object param);
	Object visit(Brnz ast, Object param);
	Object visit(Bro ast, Object param);
	Object visit(Brs ast, Object param);
	Object visit(Brz ast, Object param);
	Object visit(Call ast, Object param);
	Object visit(Cli ast, Object param);
	Object visit(Cmp ast, Object param);
	Object visit(Dec ast, Object param);
	Object visit(Halt halt, Object param);
	Object visit(Inc ast, Object param);
	Object visit(Int ast, Object param);
	Object visit(Iret ast, Object param);
	Object visit(Jmp ast, Object param);
	Object visit(Label ast, Object param);
	Object visit(Mov ast, Object param);
	Object visit(Movh ast, Object param);
	Object visit(Movl ast, Object param);
	Object visit(Neg ast, Object param);
	Object visit(Nop ast, Object param);
	Object visit(Not ast, Object param);
	Object visit(Or ast, Object param);
	Object visit(Pop ast, Object param);
	Object visit(Push ast, Object param);
	Object visit(Ret ast, Object param);
	Object visit(Sti ast, Object param);
	Object visit(Sub ast, Object param);
	Object visit(Xor ast, Object param);
	
}
