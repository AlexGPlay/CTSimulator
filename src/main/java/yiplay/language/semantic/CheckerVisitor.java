package yiplay.language.semantic;

import yiplay.language.ast.expression.BinaryLiteral;
import yiplay.language.ast.expression.HexLiteral;
import yiplay.language.ast.expression.IntegerLiteral;
import yiplay.language.ast.expression.Register;
import yiplay.language.errorManagement.ErrorManager;
import yiplay.language.visitor.AbstractVisitor;

public class CheckerVisitor extends AbstractVisitor{

	public Object visit(BinaryLiteral ast, Object param) {
		String binary = ast.translate();
		
		if(binary.length()>8)
			ErrorManager.getManager().addError(ErrorManager.LENGTH, ast.getLine(), 
					ast.getColumn(), 
					String.format("Binary maximum size is 8 bits but is %d bits long", binary.length()));
		
		return null;
	}

	public Object visit(HexLiteral ast, Object param) {
		String binary = ast.translate();
		
		if(binary.length()>8)
			ErrorManager.getManager().addError(ErrorManager.LENGTH, ast.getLine(), 
					ast.getColumn(), 
					String.format("Binary maximum size is 8 bits but is %d bits long", binary.length()));
		
		return null;
	}

	public Object visit(IntegerLiteral ast, Object param) {
		String binary = ast.translate();
		
		if(binary.length()>8)
			ErrorManager.getManager().addError(ErrorManager.LENGTH, ast.getLine(), 
					ast.getColumn(), 
					String.format("Binary maximum size is 8 bits but is %d bits long", binary.length()));
		
		return null;
	}

	public Object visit(Register ast, Object param) {
		String binary = ast.translate();
		
		if(binary.length()>3)
			ErrorManager.getManager().addError(ErrorManager.LENGTH, ast.getLine(), 
					ast.getColumn(), 
					String.format("Binary maximum size is 3 bits but is %d bits long", binary.length()));
		
		return null;
	}
	
}
