package yiplay.language.semantic;

import java.util.ArrayList;
import java.util.List;

import yiplay.language.ast.Program;
import yiplay.language.ast.Statement;
import yiplay.language.ast.expression.IntegerLiteral;
import yiplay.language.ast.statement.Brc;
import yiplay.language.ast.statement.Brnc;
import yiplay.language.ast.statement.Brno;
import yiplay.language.ast.statement.Brns;
import yiplay.language.ast.statement.Brnz;
import yiplay.language.ast.statement.Bro;
import yiplay.language.ast.statement.Brs;
import yiplay.language.ast.statement.Brz;
import yiplay.language.ast.statement.Call;
import yiplay.language.ast.statement.Jmp;
import yiplay.language.ast.statement.Label;
import yiplay.language.errorManagement.ErrorManager;
import yiplay.language.visitor.AbstractVisitor;

public class SubstitutionVisitor extends AbstractVisitor{

	private List<Label> labels = new ArrayList<Label>();
	
	private int getLabel(String labelName) {
		Label label = new Label(0,0,labelName);
		
		for(int i=0;i<labels.size();i++)
			if(labels.get(i).equals(label))
				return i;
		
		return -1;
	}
	
	public Object visit(Program ast, Object param) {
		for(Statement statement : ast.getStatements())
			if(statement instanceof Label)
				statement.accept(this, param);
		
		for(Statement statement : ast.getStatements())
			if(!(statement instanceof Label))
				statement.accept(this, param);
		
		return null;
	}
	
	public Object visit(Label ast, Object param) {
		labels.add(ast);
		return null;
	}
	
	public Object visit(Brc ast, Object param) {
		if(ast.getLines() == null) {
			int position = getLabel(ast.getLabel());
			
			if(position==-1) {
				ErrorManager.getManager().addError(ErrorManager.LABEL, ast.getLine(), ast.getColumn(), 
						String.format("Label %s doesn't exist", ast.getLabel()));
			}
			else {
				int line = labels.get(position).getLine()-1;
				int positionDifference = line-position-1;
				
				IntegerLiteral literal = new IntegerLiteral(ast.getLine(),ast.getColumn(),positionDifference);
				ast.setLines(literal);
			}
		}
		
		return null;
	}

	public Object visit(Brnc ast, Object param) {
		if(ast.getLines() == null) {
			int position = getLabel(ast.getLabel());
			
			if(position==-1) {
				ErrorManager.getManager().addError(ErrorManager.LABEL, ast.getLine(), ast.getColumn(), 
						String.format("Label %s doesn't exist", ast.getLabel()));
			}
			else {
				int line = labels.get(position).getLine()-1;
				int positionDifference = line-position-1;
				
				IntegerLiteral literal = new IntegerLiteral(ast.getLine(),ast.getColumn(),positionDifference);
				ast.setLines(literal);
			}
		}
		
		return null;
	}

	public Object visit(Brno ast, Object param) {
		if(ast.getLines() == null) {
			int position = getLabel(ast.getLabel());
			
			if(position==-1) {
				ErrorManager.getManager().addError(ErrorManager.LABEL, ast.getLine(), ast.getColumn(), 
						String.format("Label %s doesn't exist", ast.getLabel()));
			}
			else {
				int line = labels.get(position).getLine()-1;
				int positionDifference = line-position-1;
				
				IntegerLiteral literal = new IntegerLiteral(ast.getLine(),ast.getColumn(),positionDifference);
				ast.setLines(literal);
			}
		}
		
		return null;
	}

	public Object visit(Brns ast, Object param) {
		if(ast.getLines() == null) {
			int position = getLabel(ast.getLabel());
			
			if(position==-1) {
				ErrorManager.getManager().addError(ErrorManager.LABEL, ast.getLine(), ast.getColumn(), 
						String.format("Label %s doesn't exist", ast.getLabel()));
			}
			else {
				int line = labels.get(position).getLine()-1;
				int positionDifference = line-position-1;
				
				IntegerLiteral literal = new IntegerLiteral(ast.getLine(),ast.getColumn(),positionDifference);
				ast.setLines(literal);
			}
		}
		
		return null;
	}

	public Object visit(Brnz ast, Object param) {
		if(ast.getLines() == null) {
			int position = getLabel(ast.getLabel());
			
			if(position==-1) {
				ErrorManager.getManager().addError(ErrorManager.LABEL, ast.getLine(), ast.getColumn(), 
						String.format("Label %s doesn't exist", ast.getLabel()));
			}
			else {
				int line = labels.get(position).getLine()-1;
				int positionDifference = line-position-1;
				
				IntegerLiteral literal = new IntegerLiteral(ast.getLine(),ast.getColumn(),positionDifference);
				ast.setLines(literal);
			}
		}
		
		return null;
	}

	public Object visit(Bro ast, Object param) {
		if(ast.getLines() == null) {
			int position = getLabel(ast.getLabel());
			
			if(position==-1) {
				ErrorManager.getManager().addError(ErrorManager.LABEL, ast.getLine(), ast.getColumn(), 
						String.format("Label %s doesn't exist", ast.getLabel()));
			}
			else {
				int line = labels.get(position).getLine()-1;
				int positionDifference = line-position-1;
				
				IntegerLiteral literal = new IntegerLiteral(ast.getLine(),ast.getColumn(),positionDifference);
				ast.setLines(literal);
			}
		}
		
		return null;
	}

	public Object visit(Brs ast, Object param) {
		if(ast.getLines() == null) {
			int position = getLabel(ast.getLabel());
			
			if(position==-1) {
				ErrorManager.getManager().addError(ErrorManager.LABEL, ast.getLine(), ast.getColumn(), 
						String.format("Label %s doesn't exist", ast.getLabel()));
			}
			else {
				int line = labels.get(position).getLine()-1;
				int positionDifference = line-position-1;
				
				IntegerLiteral literal = new IntegerLiteral(ast.getLine(),ast.getColumn(),positionDifference);
				ast.setLines(literal);
			}
		}
		
		return null;
	}

	public Object visit(Brz ast, Object param) {
		if(ast.getLines() == null) {
			int position = getLabel(ast.getLabel());
			
			if(position==-1) {
				ErrorManager.getManager().addError(ErrorManager.LABEL, ast.getLine(), ast.getColumn(), 
						String.format("Label %s doesn't exist", ast.getLabel()));
			}
			else {
				int line = labels.get(position).getLine()-1;
				int positionDifference = line-position-1;
				
				IntegerLiteral literal = new IntegerLiteral(ast.getLine(),ast.getColumn(),positionDifference);
				ast.setLines(literal);
			}
		}
		
		return null;
	}
	
	public Object visit(Call ast, Object param) {
		if(ast.getLines() == null) {
			int position = getLabel(ast.getLabel());
			
			if(position==-1) {
				ErrorManager.getManager().addError(ErrorManager.LABEL, ast.getLine(), ast.getColumn(), 
						String.format("Label %s doesn't exist", ast.getLabel()));
			}
			else {
				int line = labels.get(position).getLine()-1;
				int positionDifference = line-ast.getLine()-position-1;
				
				IntegerLiteral literal = new IntegerLiteral(ast.getLine(),ast.getColumn(),positionDifference);
				ast.setLines(literal);
			}
		}
		
		return null;
	}
	
	public Object visit(Jmp ast, Object param) {
		if(ast.getLines() == null) {
			int position = getLabel(ast.getLabel());
			
			if(position==-1) {
				ErrorManager.getManager().addError(ErrorManager.LABEL, ast.getLine(), ast.getColumn(), 
						String.format("Label %s doesn't exist", ast.getLabel()));
			}
			else {
				int line = labels.get(position).getLine()-1;
				int positionDifference = line-position-1;
				
				IntegerLiteral literal = new IntegerLiteral(ast.getLine(),ast.getColumn(),positionDifference);
				ast.setLines(literal);
			}
		}
		
		return null;
	}
	
}
