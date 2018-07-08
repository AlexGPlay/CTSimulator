package yiplay.language.visitor;

import yiplay.language.ast.*;
import yiplay.language.ast.expression.*;
import yiplay.language.ast.statement.*;

public abstract class AbstractVisitor implements Visitor{

	// PROGRAM
	
	public Object visit(Program ast, Object param) {
		for(Statement statement : ast.getStatements())
			statement.accept(this, param);
		
		return null;
	}
	
	
	// EXPRESSIONS

	public Object visit(BinaryLiteral ast, Object param) {
		return ast.getValue();
	}

	public Object visit(HexLiteral ast, Object param) {
		return ast.getValue();
	}

	public Object visit(IntegerLiteral ast, Object param) {
		return ast.getValue();
	}

	public Object visit(Register ast, Object param) {
		return ast.getRegister();
	}

	public Object visit(Add ast, Object param) {
		ast.getRd().accept(this, param);
		ast.getRs1().accept(this, param);
		ast.getRs2().accept(this, param);
		
		return null;
	}
	
	
	// STATEMENTS

	public Object visit(And ast, Object param) {
		ast.getRd().accept(this, param);
		ast.getRs1().accept(this, param);
		ast.getRs2().accept(this, param);
		
		return null;
	}

	public Object visit(Brc ast, Object param) {
		if(ast.getLines() != null)
			ast.getLines().accept(this, param);
		
		return null;
	}

	public Object visit(Brnc ast, Object param) {
		if(ast.getLines() != null)
			ast.getLines().accept(this, param);
		
		return null;
	}

	public Object visit(Brno ast, Object param) {
		if(ast.getLines() != null)
			ast.getLines().accept(this, param);
		
		return null;
	}

	public Object visit(Brns ast, Object param) {
		if(ast.getLines() != null)
			ast.getLines().accept(this, param);
		
		return null;
	}

	public Object visit(Brnz ast, Object param) {
		if(ast.getLines() != null)
			ast.getLines().accept(this, param);
		
		return null;
	}

	public Object visit(Bro ast, Object param) {
		if(ast.getLines() != null)
			ast.getLines().accept(this, param);
		
		return null;
	}

	public Object visit(Brs ast, Object param) {
		if(ast.getLines() != null)
			ast.getLines().accept(this, param);
		
		return null;
	}

	public Object visit(Brz ast, Object param) {
		if(ast.getLines() != null)
			ast.getLines().accept(this, param);
		
		return null;
	}

	public Object visit(Call ast, Object param) {
		if(ast.getLines() != null)
			ast.getLines().accept(this, param);
		
		return null;
	}

	public Object visit(Cli ast, Object param) {
		return null;
	}

	public Object visit(Cmp ast, Object param) {
		ast.getRs1().accept(this, param);
		ast.getRs2().accept(this, param);
		
		return null;
	}

	public Object visit(Dec ast, Object param) {
		ast.getRsd().accept(this, param);
		
		return null;
	}

	public Object visit(Inc ast, Object param) {
		ast.getRsd().accept(this, param);
		
		return null;
	}

	public Object visit(Int ast, Object param) {
		ast.getInterruption().accept(this, param);
		
		return null;
	}

	public Object visit(Iret ast, Object param) {
		return null;
	}

	public Object visit(Jmp ast, Object param) {
		if(ast.getLines() != null)
			ast.getLines().accept(this, param);
		
		return null;
	}

	public Object visit(Label ast, Object param) {
		return ast.getLabel();
	}

	public Object visit(Mov ast, Object param) {
		ast.getRd().accept(this, param);
		ast.getRs().accept(this, param);
		
		return null;
	}

	public Object visit(Movh ast, Object param) {
		ast.getNumber().accept(this, param);
		ast.getRegister().accept(this, param);
		
		return null;
	}

	public Object visit(Movl ast, Object param) {
		ast.getNumber().accept(this, param);
		ast.getRegister().accept(this, param);
		
		return null;
	}

	public Object visit(Neg ast, Object param) {
		ast.getRsd().accept(this, param);
		
		return null;
	}

	public Object visit(Nop ast, Object param) {
		return null;
	}

	public Object visit(Not ast, Object param) {
		ast.getRsd().accept(this, param);
		
		return null;
	}

	public Object visit(Or ast, Object param) {
		ast.getRd().accept(this, param);
		ast.getRs1().accept(this, param);
		ast.getRs2().accept(this, param);
		
		return null;
	}

	public Object visit(Pop ast, Object param) {
		ast.getRegister().accept(this, param);
		
		return null;
	}

	public Object visit(Push ast, Object param) {
		ast.getRegister().accept(this, param);
		
		return null;
	}

	public Object visit(Ret ast, Object param) {
		return null;
	}

	public Object visit(Sti ast, Object param) {
		return null;
	}

	public Object visit(Sub ast, Object param) {
		ast.getRd().accept(this, param);
		ast.getRs1().accept(this, param);
		ast.getRs2().accept(this, param);
		
		return null;
	}

	public Object visit(Xor ast, Object param) {
		ast.getRd().accept(this, param);
		ast.getRs1().accept(this, param);
		ast.getRs2().accept(this, param);
		
		return null;
	}

}
