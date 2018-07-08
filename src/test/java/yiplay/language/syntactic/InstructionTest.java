package yiplay.language.syntactic;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import yiplay.language.ast.ASTNode;
import yiplay.language.ast.expression.*;
import yiplay.language.ast.statement.*;
import yiplay.language.errorManagement.ErrorManager;
import yiplay.language.ast.*;
import yiplay.language.lexicon.Lexicon;

public class InstructionTest {

	public ASTNode compile(String code) {
		code = code.toUpperCase();
		StringReader reader = new StringReader(code);

		Lexicon lexico = new Lexicon(reader);
		Parser parser = new Parser(lexico);
		parser.run();
		
		if(ErrorManager.getManager().hasErrors())
			ErrorManager.getManager().printErrors();

		return parser.getAST();
	}

	@Test
	public void testProgram() {
		Program program = (Program)compile("movl r3, 4 ;  Pasar el primer parámetro en r1 \r\n" + 
				"movh r3, 00\r\n" + 
				"movl r4, 7 ;  Pasar el segundo parámetro en r2\r\n" + 
				"movh r4, 0\r\n" + 
				"push r4 \r\n" + 
				"push r3 \r\n" + 
				"call suma\r\n" + 
				"inc r7\r\n" + 
				"inc r7\r\n" + 
				"movl r3, 0\r\n" + 
				"movh r3, 10h\r\n" + 
				"mov [r3], r0\r\n" + 
				"\r\n" + 
				"suma: \r\n" + 
				"    push r6 ; Prólogo\r\n" + 
				"    mov r6, r7\r\n" + 
				"\r\n" + 
				"    push r1 ; Salvaguarda de registros\r\n" + 
				"    push r2 \r\n" + 
				"\r\n" + 
				"    inc r6\r\n" + 
				"    inc r6\r\n" + 
				"    mov r1, [r6] ; Acceso al primer parámetro\r\n" + 
				"    inc r6\r\n" + 
				"    mov r2, [r6] ; Acceso al segundo parámetro\r\n" + 
				"\r\n" + 
				"    add r0, r1, r2 ; Cuerpo del procedimiento\r\n" + 
				"\r\n" + 
				"    pop r2 ; Restauración de registros\r\n" + 
				"    pop r1\r\n" + 
				"\r\n" + 
				"    pop r6 ; Epílogo\r\n" + 
				"    ret ");


		assertEquals(27, program.getStatements().size());
	}

	@Test
	public void testAdd() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				for(int z=0;z<8;z++) {
					String code = "Add R" + i + ", R" + j + ", R" + z;
					Register rd = new Register(0,0,"R" + i);
					Register rs1 = new Register(0,0,"R" + j);
					Register rs2 = new Register(0,0,"R" + z);
					Add add = new Add(0,0,rd,rs1,rs2);

					Program ast = (Program)compile(code);
					Statement compiled = ast.getStatements().get(0);
					assertEquals(add,compiled);
				}
			}
		}
	}

	@Test
	public void testAnd() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				for(int z=0;z<8;z++) {
					String code = "And R" + i + ", R" + j + ", R" + z;
					Register rd = new Register(0,0,"R" + i);
					Register rs1 = new Register(0,0,"R" + j);
					Register rs2 = new Register(0,0,"R" + z);
					And and = new And(0,0,rd,rs1,rs2);

					Program ast = (Program)compile(code);
					Statement compiled = ast.getStatements().get(0);
					assertEquals(and,compiled);
				}
			}
		}
	}

	@Test
	public void testBrc() {
		String[] labels = {"SALTO", "SALTO22"};
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};

		for(String temp : labels) {
			Statement testing = new Brc(0,0,temp);
			String code = "Brc " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Brc(0,0,literal);
			String code = "Brc " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Brc(0,0,literal);
			String code = "Brc " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Brc(0,0,literal);
			String code = "Brc " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

	}

	@Test
	public void testBrnc() {
		String[] labels = {"SALTO", "SALTO22"};
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};

		for(String temp : labels) {
			Statement testing = new Brnc(0,0,temp);
			String code = "Brnc " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Brnc(0,0,literal);
			String code = "Brnc " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Brnc(0,0,literal);
			String code = "Brnc " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Brnc(0,0,literal);
			String code = "Brnc " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

	}

	@Test
	public void testBrno() {
		String[] labels = {"SALTO", "SALTO22"};
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};

		for(String temp : labels) {
			Statement testing = new Brno(0,0,temp);
			String code = "Brno " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Brno(0,0,literal);
			String code = "Brno " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Brno(0,0,literal);
			String code = "Brno " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Brno(0,0,literal);
			String code = "Brno " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

	}

	@Test
	public void testBrns() {
		String[] labels = {"SALTO", "SALTO22"};
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};

		for(String temp : labels) {
			Statement testing = new Brns(0,0,temp);
			String code = "Brns " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Brns(0,0,literal);
			String code = "Brns " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Brns(0,0,literal);
			String code = "Brns " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Brns(0,0,literal);
			String code = "Brns " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

	}

	@Test
	public void testBrnz() {
		String[] labels = {"SALTO", "SALTO22"};
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};

		for(String temp : labels) {
			Statement testing = new Brnz(0,0,temp);
			String code = "Brnz " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Brnz(0,0,literal);
			String code = "Brnz " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Brnz(0,0,literal);
			String code = "Brnz " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Brnz(0,0,literal);
			String code = "Brnz " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

	}

	@Test
	public void testBro() {
		String[] labels = {"SALTO", "SALTO22"};
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};

		for(String temp : labels) {
			Statement testing = new Bro(0,0,temp);
			String code = "Bro " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Bro(0,0,literal);
			String code = "Bro " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Bro(0,0,literal);
			String code = "Bro " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Bro(0,0,literal);
			String code = "Bro " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

	}

	@Test
	public void testBrs() {
		String[] labels = {"SALTO", "SALTO22"};
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};

		for(String temp : labels) {
			Statement testing = new Brs(0,0,temp);
			String code = "Brs " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Brs(0,0,literal);
			String code = "Brs " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Brs(0,0,literal);
			String code = "Brs " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Brs(0,0,literal);
			String code = "Brs " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

	}

	@Test
	public void testBrz() {
		String[] labels = {"SALTO", "SALTO22"};
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};

		for(String temp : labels) {
			Statement testing = new Brz(0,0,temp);
			String code = "Brz " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Brz(0,0,literal);
			String code = "Brz " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Brz(0,0,literal);
			String code = "Brz " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Brz(0,0,literal);
			String code = "Brz " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

	}

	@Test
	public void testCall() {
		String[] labels = {"SALTO", "SALTO22"};
		String[] registers = {"R0", "R1", "R2", "R3"};
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};

		for(String temp : labels) {
			Statement testing = new Call(0,0,temp);
			String code = "Call " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Call(0,0,literal,0);
			String code = "Call " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Call(0,0,literal,0);
			String code = "Call " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Call(0,0,literal,0);
			String code = "Call " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : registers) {
			Register reg = new Register(0,0,temp);
			Statement testing = new Call(0,0,reg,1);
			String code = "Call " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

	}

	@Test
	public void testCli() {
		Statement cli = new Cli(0,0);
		String code = "Cli";
		Program ast = (Program)compile(code);
		assertEquals(cli,ast.getStatements().get(0));
	}

	@Test
	public void testCmp() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				String code = "Cmp R" + i + ", R" + j;
				Register rs1 = new Register(0,0,"R" + i);
				Register rs2 = new Register(0,0,"R" + j);
				Statement add = new Cmp(0,0,rs1,rs2);

				Program ast = (Program)compile(code);
				Statement compiled = ast.getStatements().get(0);
				assertEquals(add,compiled);
			}
		}
	}

	@Test
	public void testDec() {
		for(int i=0;i<8;i++) {
			String code = "Dec R" + i;
			Register rsd = new Register(0,0,"R" + i);
			Statement add = new Dec(0,0,rsd);

			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(add,compiled);
		}
	}
	
	@Test
	public void testInc() {
		for(int i=0;i<8;i++) {
			String code = "Inc R" + i;
			Register rsd = new Register(0,0,"R" + i);
			Statement add = new Inc(0,0,rsd);

			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(add,compiled);
		}
	}
	
	@Test
	public void testInt() {
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};

		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Int(0,0,literal);
			String code = "Int " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Int(0,0,literal);
			String code = "Int " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Int(0,0,literal);
			String code = "Int " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

	}
	
	@Test
	public void testIret() {
		Statement iret = new Iret(0,0);
		String code = "iret";
		Program ast = (Program)compile(code);
		assertEquals(iret,ast.getStatements().get(0));
	}
	
	@Test
	public void testJmp() {
		String[] labels = {"SALTO", "SALTO22"};
		String[] registers = {"R0", "R1", "R2", "R3"};
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};

		for(String temp : labels) {
			Statement testing = new Jmp(0,0,temp);
			String code = "Jmp " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Jmp(0,0,literal,0);
			String code = "Jmp " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Jmp(0,0,literal,0);
			String code = "Jmp " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Jmp(0,0,literal,0);
			String code = "Jmp " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : registers) {
			Register reg = new Register(0,0,temp);
			Statement testing = new Jmp(0,0,reg,1);
			String code = "Jmp " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

	}
	
	@Test
	public void testLabel() {
		String[] labels = {"TEST", "SALTO"};
		
		for(String temp : labels) {
			Label label = new Label(0,0,temp);
			String code = temp + ":";
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(label,compiled);
		}
		
	}
	
	@Test
	public void testMov() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				for(int z=0;z<3;z++) {
					String code = null;
					Statement mov = null;
					
					Register rd = new Register(0,0,"R" + i);
					Register rs = new Register(0,0,"R" + j);
					
					if(z == 0) {
						code = "Mov R" + i + ", R" + j;
						mov = new Mov(0,0,rd,rs,z);
					}
					else if(z == 1) {
						code = "Mov R" + i + ",[R" + j + "]";
						mov = new Mov(0,0,rd,rs,z);
					}
					else if(z == 2) {
						code = "Mov [R" + i + "], R" + j;
						mov = new Mov(0,0,rd,rs,z);
					}
					
					Program ast = (Program)compile(code);
					Statement compiled = ast.getStatements().get(0);
					assertEquals(mov,compiled);
				}
			}
		}
	}
	
	@Test
	public void testeMovh() {
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};
		Register reg = new Register(0,0,"R0");
		
		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Movh(0,0,reg,literal);
			String code = "Movh R0, " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Movh(0,0,reg,literal);
			String code = "Movh R0, " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Movh(0,0,reg,literal);
			String code = "Movh R0, " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}
	}
	
	@Test
	public void testeMovl() {
		String[] hexadec = {"0H1234", "1234H"};
		String[] binary = {"0B11011"};
		Integer[] decimal = {1234, 134};
		Register reg = new Register(0,0,"R0");
		
		for(String temp : hexadec) {
			HexLiteral literal = new HexLiteral(0,0,temp);
			Statement testing = new Movl(0,0,reg,literal);
			String code = "Movl R0, " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(String temp : binary) {
			BinaryLiteral literal = new BinaryLiteral(0,0,temp);
			Statement testing = new Movl(0,0,reg,literal);
			String code = "Movl R0, " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}

		for(Integer temp : decimal) {
			IntegerLiteral literal = new IntegerLiteral(0,0,temp);
			Statement testing = new Movl(0,0,reg,literal);
			String code = "Movl R0, " + temp;
			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(testing,compiled);
		}
	}
	
	@Test
	public void testNeg() {
		for(int i=0;i<8;i++) {
			String code = "Neg R" + i;
			Register rsd = new Register(0,0,"R" + i);
			Statement add = new Neg(0,0,rsd);

			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(add,compiled);
		}
	}

	@Test
	public void testNop() {
		Statement iret = new Nop(0,0);
		String code = "Nop";
		Program ast = (Program)compile(code);
		assertEquals(iret,ast.getStatements().get(0));
	}
	
	@Test
	public void testNot() {
		for(int i=0;i<8;i++) {
			String code = "Not R" + i;
			Register rsd = new Register(0,0,"R" + i);
			Statement add = new Not(0,0,rsd);

			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(add,compiled);
		}
	}
	
	@Test
	public void testOr() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				for(int z=0;z<8;z++) {
					String code = "Or R" + i + ", R" + j + ", R" + z;
					Register rd = new Register(0,0,"R" + i);
					Register rs1 = new Register(0,0,"R" + j);
					Register rs2 = new Register(0,0,"R" + z);
					Statement add = new Or(0,0,rd,rs1,rs2);

					Program ast = (Program)compile(code);
					Statement compiled = ast.getStatements().get(0);
					assertEquals(add,compiled);
				}
			}
		}
	}
	
	@Test
	public void testPop() {
		for(int i=0;i<8;i++) {
			String code = "Pop R" + i;
			Register rsd = new Register(0,0,"R" + i);
			Statement add = new Pop(0,0,rsd);

			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(add,compiled);
		}
	}
	
	@Test
	public void testPush() {
		for(int i=0;i<8;i++) {
			String code = "Push R" + i;
			Register rsd = new Register(0,0,"R" + i);
			Statement add = new Push(0,0,rsd);

			Program ast = (Program)compile(code);
			Statement compiled = ast.getStatements().get(0);
			assertEquals(add,compiled);
		}
	}
	
	@Test
	public void testRet() {
		Statement iret = new Ret(0,0);
		String code = "Ret";
		Program ast = (Program)compile(code);
		assertEquals(iret,ast.getStatements().get(0));
	}
	
	@Test
	public void testSti() {
		Statement iret = new Sti(0,0);
		String code = "Sti";
		Program ast = (Program)compile(code);
		assertEquals(iret,ast.getStatements().get(0));
	}
	
	@Test
	public void testSub() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				for(int z=0;z<8;z++) {
					String code = "Sub R" + i + ", R" + j + ", R" + z;
					Register rd = new Register(0,0,"R" + i);
					Register rs1 = new Register(0,0,"R" + j);
					Register rs2 = new Register(0,0,"R" + z);
					Statement add = new Sub(0,0,rd,rs1,rs2);

					Program ast = (Program)compile(code);
					Statement compiled = ast.getStatements().get(0);
					assertEquals(add,compiled);
				}
			}
		}
	}
	
	@Test
	public void testXor() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				for(int z=0;z<8;z++) {
					String code = "Xor R" + i + ", R" + j + ", R" + z;
					Register rd = new Register(0,0,"R" + i);
					Register rs1 = new Register(0,0,"R" + j);
					Register rs2 = new Register(0,0,"R" + z);
					Statement add = new Xor(0,0,rd,rs1,rs2);

					Program ast = (Program)compile(code);
					Statement compiled = ast.getStatements().get(0);
					assertEquals(add,compiled);
				}
			}
		}
	}
	
}
