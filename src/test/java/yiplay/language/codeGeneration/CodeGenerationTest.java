package yiplay.language.codeGeneration;

import static org.junit.Assert.*;

import java.util.List;
import yiplay.language.Compiler;
import yiplay.language.ast.expression.*;

import org.junit.Test;

public class CodeGenerationTest {

	public List<String> compile(String code){
		Compiler compiler = new Compiler();
		return compiler.compile(code);
	}

	@Test
	public void testAdd() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				for(int w=0;w<8;w++) {
					Register rd = new Register(0,0,"R"+i);
					Register rs1 = new Register(0,0,"R"+j);
					Register rs2 = new Register(0,0,"R"+w);

					String add = String.format(CodeGenerationVisitor.ADD,rd.translate(),rs1.translate(),rs2.translate());

					String code = String.format("ADD R%d, R%d, R%d", i,j,w);
					String res = compile(code).get(0);

					assertEquals(add,res);
				}
			}
		}
	}

	@Test
	public void testAnd() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				for(int w=0;w<8;w++) {
					Register rd = new Register(0,0,"R"+i);
					Register rs1 = new Register(0,0,"R"+j);
					Register rs2 = new Register(0,0,"R"+w);

					String add = String.format(CodeGenerationVisitor.AND,rd.translate(),rs1.translate(),rs2.translate());

					String code = String.format("AND R%d, R%d, R%d", i,j,w);
					String res = compile(code).get(0);

					assertEquals(add,res);
				}
			}
		}
	}

	@Test
	public void testBrc1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRC, register.translate());

			String code = String.format("BRC %d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testBrc2() {
		String[] labels = {"test", "test2", "test3"};

		for(int i=0;i<labels.length;i++) {
			String code = String.format("BRC %s \r\n %s:", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,0);
			String brc = String.format(CodeGenerationVisitor.BRC, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

		for(int i=0;i<labels.length;i++) {
			String code = String.format("%s: \r\n BRC %s", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,-2);
			String brc = String.format(CodeGenerationVisitor.BRC, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

	}

	@Test
	public void testBrnc1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRNC, register.translate());

			String code = String.format("BRNC %d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testBrnc2() {
		String[] labels = {"test", "test2", "test3"};

		for(int i=0;i<labels.length;i++) {
			String code = String.format("BRNC %s \r\n %s:", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,0);
			String brc = String.format(CodeGenerationVisitor.BRNC, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

		for(int i=0;i<labels.length;i++) {
			String code = String.format("%s: \r\n BRNC %s", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,-2);
			String brc = String.format(CodeGenerationVisitor.BRNC, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

	}

	@Test
	public void testBrno1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRNO, register.translate());

			String code = String.format("BRNO %d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testBrno2() {
		String[] labels = {"test", "test2", "test3"};

		for(int i=0;i<labels.length;i++) {
			String code = String.format("BRNO %s \r\n %s:", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,0);
			String brc = String.format(CodeGenerationVisitor.BRNO, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

		for(int i=0;i<labels.length;i++) {
			String code = String.format("%s: \r\n BRNO %s", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,-2);
			String brc = String.format(CodeGenerationVisitor.BRNO, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

	}

	@Test
	public void testBrns1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRNS, register.translate());

			String code = String.format("BRNS %d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testBrns2() {
		String[] labels = {"test", "test2", "test3"};

		for(int i=0;i<labels.length;i++) {
			String code = String.format("BRNS %s \r\n %s:", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,0);
			String brc = String.format(CodeGenerationVisitor.BRNS, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

		for(int i=0;i<labels.length;i++) {
			String code = String.format("%s: \r\n BRNS %s", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,-2);
			String brc = String.format(CodeGenerationVisitor.BRNS, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

	}

	@Test
	public void testBrnz1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRNZ, register.translate());

			String code = String.format("BRNZ %d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testBrnz2() {
		String[] labels = {"test", "test2", "test3"};

		for(int i=0;i<labels.length;i++) {
			String code = String.format("BRNZ %s \r\n %s:", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,0);
			String brc = String.format(CodeGenerationVisitor.BRNZ, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

		for(int i=0;i<labels.length;i++) {
			String code = String.format("%s: \r\n BRNZ %s", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,-2);
			String brc = String.format(CodeGenerationVisitor.BRNZ, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

	}

	@Test
	public void testBro1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRO, register.translate());

			String code = String.format("BRO %d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testBro2() {
		String[] labels = {"test", "test2", "test3"};

		for(int i=0;i<labels.length;i++) {
			String code = String.format("BRO %s \r\n %s:", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,0);
			String brc = String.format(CodeGenerationVisitor.BRO, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

		for(int i=0;i<labels.length;i++) {
			String code = String.format("%s: \r\n BRO %s", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,-2);
			String brc = String.format(CodeGenerationVisitor.BRO, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

	}

	@Test
	public void testBrs1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRNS, register.translate());

			String code = String.format("BRNS %d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testBrs2() {
		String[] labels = {"test", "test2", "test3"};

		for(int i=0;i<labels.length;i++) {
			String code = String.format("BRS %s \r\n %s:", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,0);
			String brc = String.format(CodeGenerationVisitor.BRS, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

		for(int i=0;i<labels.length;i++) {
			String code = String.format("%s: \r\n BRS %s", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,-2);
			String brc = String.format(CodeGenerationVisitor.BRS, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

	}

	@Test
	public void testBrz1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRZ, register.translate());

			String code = String.format("BRZ %d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testBrz2() {
		String[] labels = {"test", "test2", "test3"};

		for(int i=0;i<labels.length;i++) {
			String code = String.format("BRZ %s \r\n %s:", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,0);
			String brc = String.format(CodeGenerationVisitor.BRZ, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

		for(int i=0;i<labels.length;i++) {
			String code = String.format("%s: \r\n BRZ %s", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,-2);
			String brc = String.format(CodeGenerationVisitor.BRZ, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

	}

	@Test
	public void testCall1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.CALL8, register.translate());

			String code = String.format("CALL %d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testCall2() {
		String[] labels = {"test", "test2", "test3"};

		for(int i=0;i<labels.length;i++) {
			String code = String.format("CALL %s \r\n %s:", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,0);
			String brc = String.format(CodeGenerationVisitor.CALL8, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

		for(int i=0;i<labels.length;i++) {
			String code = String.format("%s: \r\n CALL %s", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,-2);
			String brc = String.format(CodeGenerationVisitor.CALL8, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

	}

	@Test
	public void testCall3() {
		for(int i=0;i<8;i++) {
			Register register = new Register(0,0,"R"+i);

			String brc = String.format(CodeGenerationVisitor.CALLR, register.translate());

			String code = String.format("CALL R%d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testCli() {
		String code = "CLI";
		String cli = String.format(CodeGenerationVisitor.CLI);

		String res = compile(code).get(0);

		assertEquals(cli,res);
	}

	@Test
	public void testCmp() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Register rs1 = new Register(0,0,"R"+i);
				Register rs2 = new Register(0,0,"R"+j);

				String cmp = String.format(CodeGenerationVisitor.CMP, rs1.translate(),rs2.translate());

				String code = String.format("CMP R%d, R%d", i,j);
				String res = compile(code).get(0);

				assertEquals(cmp,res);
			}
		}
	}

	@Test
	public void testDec() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String dec = String.format(CodeGenerationVisitor.DEC, rsd.translate());

			String code = String.format("DEC R%d", i);
			String res = compile(code).get(0);

			assertEquals(dec,res);
		}
	}

	@Test
	public void testInc() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String inc = String.format(CodeGenerationVisitor.INC, rsd.translate());

			String code = String.format("INC R%d", i);
			String res = compile(code).get(0);

			assertEquals(inc,res);
		}
	}

	@Test
	public void testInt() {
		for(int i=0;i<100;i++) {
			IntegerLiteral literal = new IntegerLiteral(0,0,i);

			String interrupt = String.format(CodeGenerationVisitor.INT, literal.translate());

			String code = String.format("INT %d", i);
			String res = compile(code).get(0);

			assertEquals(interrupt,res);
		}
	}

	@Test
	public void testIret() {
		String code = "IRET";
		String cli = String.format(CodeGenerationVisitor.IRET);

		String res = compile(code).get(0);

		assertEquals(cli,res);
	}

	@Test
	public void testJmp1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.JMP8, register.translate());

			String code = String.format("JMP %d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testJmp2() {
		String[] labels = {"test", "test2", "test3"};

		for(int i=0;i<labels.length;i++) {
			String code = String.format("JMP %s \r\n %s:", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,0);
			String brc = String.format(CodeGenerationVisitor.JMP8, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

		for(int i=0;i<labels.length;i++) {
			String code = String.format("%s: \r\n JMP %s", labels[i], labels[i]);

			IntegerLiteral literal = new IntegerLiteral(0,0,-2);
			String brc = String.format(CodeGenerationVisitor.JMP8, literal.translate());

			String res = compile(code).get(0);

			assertEquals(brc,res);
		}

	}

	@Test
	public void testJmp3() {
		for(int i=0;i<8;i++) {
			Register register = new Register(0,0,"R"+i);

			String brc = String.format(CodeGenerationVisitor.JMPR, register.translate());

			String code = String.format("JMP R%d", i);
			String res = compile(code).get(0);

			assertEquals(brc,res);
		}
	}

	@Test
	public void testMov1() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Register rd = new Register(0,0,"R"+i);
				Register rs = new Register(0,0,"R"+j);
				
				String mov = String.format(CodeGenerationVisitor.MOV_RD_RS, rd.translate(),rs.translate());
				
				String code = String.format("MOV R%d, R%d", i,j);
				String res = compile(code).get(0);
				
				assertEquals(mov,res);
			}
		}
	}
	
	@Test
	public void testMov2() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Register rd = new Register(0,0,"R"+i);
				Register rs = new Register(0,0,"R"+j);
				
				String mov = String.format(CodeGenerationVisitor.MOV_RD_RI, rd.translate(),rs.translate());
				
				String code = String.format("MOV R%d, [R%d]", i,j);
				String res = compile(code).get(0);
				
				assertEquals(mov,res);
			}
		}
	}
	
	@Test
	public void testMov3() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Register rd = new Register(0,0,"R"+i);
				Register rs = new Register(0,0,"R"+j);
				
				String mov = String.format(CodeGenerationVisitor.MOV_RI_RS, rd.translate(),rs.translate());
				
				String code = String.format("MOV [R%d], R%d", i,j);
				String res = compile(code).get(0);
				
				assertEquals(mov,res);
			}
		}
	}
	
	@Test
	public void testMovh() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<50;j++) {
				Register register = new Register(0,0,"R"+i);
				IntegerLiteral literal = new IntegerLiteral(0,0,j);
				
				String movh = String.format(CodeGenerationVisitor.MOVH, register.translate(), literal.translate());
				String code = String.format("MOVH R%d, %s", i,j);
				
				String res = compile(code).get(0);
				
				assertEquals(movh,res);
			}
		}
	}
	
	@Test
	public void testMovl() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<50;j++) {
				Register register = new Register(0,0,"R"+i);
				IntegerLiteral literal = new IntegerLiteral(0,0,j);
				
				String movh = String.format(CodeGenerationVisitor.MOVL, register.translate(), literal.translate());
				String code = String.format("MOVL R%d, %s", i,j);
				
				String res = compile(code).get(0);
				
				assertEquals(movh,res);
			}
		}
	}
	
	@Test
	public void testNeg() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String inc = String.format(CodeGenerationVisitor.NEG, rsd.translate());

			String code = String.format("NEG R%d", i);
			String res = compile(code).get(0);

			assertEquals(inc,res);
		}
	}
	
	@Test
	public void testNop() {
		String code = "NOP";
		String cli = String.format(CodeGenerationVisitor.NOP);

		String res = compile(code).get(0);

		assertEquals(cli,res);
	}
	
	@Test
	public void testNot() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String inc = String.format(CodeGenerationVisitor.NOT, rsd.translate());

			String code = String.format("NOT R%d", i);
			String res = compile(code).get(0);

			assertEquals(inc,res);
		}
	}
	
	@Test
	public void testOr() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				for(int w=0;w<8;w++) {
					Register rd = new Register(0,0,"R"+i);
					Register rs1 = new Register(0,0,"R"+j);
					Register rs2 = new Register(0,0,"R"+w);

					String add = String.format(CodeGenerationVisitor.OR,rd.translate(),rs1.translate(),rs2.translate());

					String code = String.format("OR R%d, R%d, R%d", i,j,w);
					String res = compile(code).get(0);

					assertEquals(add,res);
				}
			}
		}
	}
	
	@Test
	public void testPop() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String inc = String.format(CodeGenerationVisitor.POP, rsd.translate());

			String code = String.format("POP R%d", i);
			String res = compile(code).get(0);

			assertEquals(inc,res);
		}
	}
	
	@Test
	public void testPush() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String inc = String.format(CodeGenerationVisitor.PUSH, rsd.translate());

			String code = String.format("PUSH R%d", i);
			String res = compile(code).get(0);

			assertEquals(inc,res);
		}
	}
	
	@Test
	public void testRet() {
		String code = "RET";
		String cli = String.format(CodeGenerationVisitor.RET);

		String res = compile(code).get(0);

		assertEquals(cli,res);
	}
	
	@Test
	public void testSti() {
		String code = "STI";
		String cli = String.format(CodeGenerationVisitor.STI);

		String res = compile(code).get(0);

		assertEquals(cli,res);
	}
	
	@Test
	public void testSub() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				for(int w=0;w<8;w++) {
					Register rd = new Register(0,0,"R"+i);
					Register rs1 = new Register(0,0,"R"+j);
					Register rs2 = new Register(0,0,"R"+w);

					String add = String.format(CodeGenerationVisitor.SUB,rd.translate(),rs1.translate(),rs2.translate());

					String code = String.format("SUB R%d, R%d, R%d", i,j,w);
					String res = compile(code).get(0);

					assertEquals(add,res);
				}
			}
		}
	}
	
	@Test
	public void testXor() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				for(int w=0;w<8;w++) {
					Register rd = new Register(0,0,"R"+i);
					Register rs1 = new Register(0,0,"R"+j);
					Register rs2 = new Register(0,0,"R"+w);

					String add = String.format(CodeGenerationVisitor.XOR,rd.translate(),rs1.translate(),rs2.translate());

					String code = String.format("XOR R%d, R%d, R%d", i,j,w);
					String res = compile(code).get(0);

					assertEquals(add,res);
				}
			}
		}
	}

}
