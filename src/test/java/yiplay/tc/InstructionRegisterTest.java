package yiplay.tc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import yiplay.language.ast.ASTNode;
import yiplay.language.ast.expression.IntegerLiteral;
import yiplay.language.ast.expression.Register;
import yiplay.language.ast.statement.*;
import yiplay.language.codeGeneration.CodeGenerationVisitor;
import yiplay.tc.cpu.bus.InternalBus;
import yiplay.tc.cpu.register.InstructionRegister;
import yiplay.util.Translate;

public class InstructionRegisterTest {

	InstructionRegister ir = (InstructionRegister) InstructionRegister.getInstance();
	
	@Test
	public void extIrl_Ib() {
		
		short data = -1;
		ir.setData(data);
		ir.ExtIrl_Ib();
		
		short lowByte = (short) (data & 0xFF);
		assertEquals(lowByte, ((InternalBus)InternalBus.getInstance()).getData());
	}
	
	@Test
	public void Irl_Ibl() {
		
		short data = -1;
		ir.setData(data);
		ir.Irl_Ibl();
		
		short lowByte = (short) (data & 0xFF);
		short highByte = (short) ((((InternalBus)InternalBus.getInstance()).getData()) & 0xFF00);
		short res = 0;
		res |= lowByte;
		res |= highByte;
		
		assertEquals(res, ((InternalBus)InternalBus.getInstance()).getData());
	}
	
	@Test
	public void Irh_Ibh() {
		
		short data = -1;
		ir.setData(data);
		ir.Irl_Ibh();
		
		short lowByte = (short) ((((InternalBus)InternalBus.getInstance()).getData()) & 0xFF);
		short highByte = (short) (data & 0xFF);
		short res = 0;
		res |= lowByte;
		res |= highByte;
		
		assertEquals(res, ((InternalBus)InternalBus.getInstance()).getData());
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
					Add res = new Add(0,0,rd,rs1,rs2);
					
					ir.setData( Translate.toDecimalInteger(add) );
					assertEquals(res,ir.getActualInstruction());
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
					And res = new And(0,0,rd,rs1,rs2);
					
					ir.setData( Translate.toDecimalInteger(add) );
					assertEquals(res,ir.getActualInstruction());
				}
			}
		}
	}

	@Test
	public void testBrc() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRC, register.translate());

			ASTNode res = new Brc(0,0,register);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testBrnc() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRNC, register.translate());

			ASTNode res = new Brnc(0,0,register);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testBrno() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRNO, register.translate());

			ASTNode res = new Brno(0,0,register);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testBrns1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRNS, register.translate());

			ASTNode res = new Brns(0,0,register);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testBrnz() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRNZ, register.translate());

			ASTNode res = new Brnz(0,0,register);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testBro() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRO, register.translate());

			ASTNode res = new Bro(0,0,register);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testBrs() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRS, register.translate());

			ASTNode res = new Brs(0,0,register);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testBrz() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.BRZ, register.translate());

			ASTNode res = new Brz(0,0,register);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testCall1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.CALL8, register.translate());

			ASTNode res = new Call(0,0,register,0);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testCall2() {
		for(int i=0;i<8;i++) {
			Register register = new Register(0,0,"R"+i);

			String brc = String.format(CodeGenerationVisitor.CALLR, register.translate());

			ASTNode res = new Call(0,0,register,1);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testCli() {
		String cli = String.format(CodeGenerationVisitor.CLI);

		ASTNode res = new Cli(0,0);

		ir.setData( Translate.toDecimalInteger(cli) );
		assertEquals(res,ir.getActualInstruction());
	}

	@Test
	public void testCmp() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Register rs1 = new Register(0,0,"R"+i);
				Register rs2 = new Register(0,0,"R"+j);

				String cmp = String.format(CodeGenerationVisitor.CMP, rs1.translate(),rs2.translate());
				
				ASTNode res = new Cmp(0,0,rs1,rs2);

				ir.setData( Translate.toDecimalInteger(cmp) );
				assertEquals(res,ir.getActualInstruction());
			}
		}
	}

	@Test
	public void testDec() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String dec = String.format(CodeGenerationVisitor.DEC, rsd.translate());

			ASTNode res = new Dec(0,0,rsd);

			ir.setData( Translate.toDecimalInteger(dec) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testInc() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String inc = String.format(CodeGenerationVisitor.INC, rsd.translate());

			ASTNode res = new Inc(0,0,rsd);

			ir.setData( Translate.toDecimalInteger(inc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testInt() {
		for(int i=0;i<100;i++) {
			IntegerLiteral literal = new IntegerLiteral(0,0,i);

			String interrupt = String.format(CodeGenerationVisitor.INT, literal.translate());

			ASTNode res = new Int(0,0,literal);

			ir.setData( Translate.toDecimalInteger(interrupt) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testIret() {
		String cli = String.format(CodeGenerationVisitor.IRET);

		ASTNode res = new Iret(0,0);

		ir.setData( Translate.toDecimalInteger(cli) );
		assertEquals(res,ir.getActualInstruction());
	}

	@Test
	public void testJmp1() {
		for(int i=0;i<100;i++) {
			IntegerLiteral register = new IntegerLiteral(0,0,i);

			String brc = String.format(CodeGenerationVisitor.JMP8, register.translate());

			ASTNode res = new Jmp(0,0,register,0);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testJmp2() {
		for(int i=0;i<8;i++) {
			Register register = new Register(0,0,"R"+i);

			String brc = String.format(CodeGenerationVisitor.JMPR, register.translate());

			ASTNode res = new Jmp(0,0,register,1);

			ir.setData( Translate.toDecimalInteger(brc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}

	@Test
	public void testMov1() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Register rd = new Register(0,0,"R"+i);
				Register rs = new Register(0,0,"R"+j);
				
				String mov = String.format(CodeGenerationVisitor.MOV_RD_RS, rd.translate(),rs.translate());
				
				ASTNode res = new Mov(0,0,rd,rs,0);

				ir.setData( Translate.toDecimalInteger(mov) );
				assertEquals(res,ir.getActualInstruction());
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
				
				ASTNode res = new Mov(0,0,rd,rs,1);

				ir.setData( Translate.toDecimalInteger(mov) );
				assertEquals(res,ir.getActualInstruction());
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
				
				ASTNode res = new Mov(0,0,rd,rs,2);

				ir.setData( Translate.toDecimalInteger(mov) );
				assertEquals(res,ir.getActualInstruction());
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
				ASTNode res = new Movh(0,0,register,literal);

				ir.setData( Translate.toDecimalInteger(movh) );
				assertEquals(res,ir.getActualInstruction());
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
				ASTNode res = new Movl(0,0,register,literal);

				ir.setData( Translate.toDecimalInteger(movh) );
				assertEquals(res,ir.getActualInstruction());
			}
		}
	}
	
	@Test
	public void testNeg() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String inc = String.format(CodeGenerationVisitor.NEG, rsd.translate());
			ASTNode res = new Neg(0,0,rsd);

			ir.setData( Translate.toDecimalInteger(inc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}
	
	@Test
	public void testNop() {
		String cli = String.format(CodeGenerationVisitor.NOP);

		ASTNode res = new Nop(0,0);

		ir.setData( Translate.toDecimalInteger(cli) );
		assertEquals(res,ir.getActualInstruction());
	}
	
	@Test
	public void testNot() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String inc = String.format(CodeGenerationVisitor.NOT, rsd.translate());
			ASTNode res = new Not(0,0,rsd);

			ir.setData( Translate.toDecimalInteger(inc) );
			assertEquals(res,ir.getActualInstruction());
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
					ASTNode res = new Or(0,0,rd,rs1,rs2);

					ir.setData( Translate.toDecimalInteger(add) );
					assertEquals(res,ir.getActualInstruction());
				}
			}
		}
	}
	
	@Test
	public void testPop() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String inc = String.format(CodeGenerationVisitor.POP, rsd.translate());
			ASTNode res = new Pop(0,0,rsd);

			ir.setData( Translate.toDecimalInteger(inc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}
	
	@Test
	public void testPush() {
		for(int i=0;i<8;i++) {
			Register rsd = new Register(0,0,"R"+i);

			String inc = String.format(CodeGenerationVisitor.PUSH, rsd.translate());
			ASTNode res = new Push(0,0,rsd);

			ir.setData( Translate.toDecimalInteger(inc) );
			assertEquals(res,ir.getActualInstruction());
		}
	}
	
	@Test
	public void testRet() {
		String cli = String.format(CodeGenerationVisitor.RET);
		ASTNode res = new Ret(0,0);

		ir.setData( Translate.toDecimalInteger(cli) );
		assertEquals(res,ir.getActualInstruction());
	}
	
	@Test
	public void testSti() {
		String cli = String.format(CodeGenerationVisitor.STI);
		ASTNode res = new Sti(0,0);

		ir.setData( Translate.toDecimalInteger(cli) );
		assertEquals(res,ir.getActualInstruction());
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
					ASTNode res = new Sub(0,0,rd,rs1,rs2);

					ir.setData( Translate.toDecimalInteger(add) );
					assertEquals(res,ir.getActualInstruction());
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
					ASTNode res = new Xor(0,0,rd,rs1,rs2);

					ir.setData( Translate.toDecimalInteger(add) );
					assertEquals(res,ir.getActualInstruction());
				}
			}
		}
	}

	
}
