package yiplay.tc.execution;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import yiplay.language.ast.expression.IntegerLiteral;
import yiplay.language.ast.expression.Register;
import yiplay.language.ast.statement.*;
import yiplay.language.codeGeneration.CodeGenerationVisitor;
import yiplay.tc.cpu.ControlUnit;
import yiplay.tc.cpu.register.GeneralPurpousesRegisters;
import yiplay.tc.cpu.register.InstructionRegister;
import yiplay.tc.cpu.register.StatusRegister;
import yiplay.tc.memory.Memory;
import yiplay.util.Translate;

public class SingleExecutionTest {

	ControlUnit cU = (ControlUnit) ControlUnit.getInstance();
	GeneralPurpousesRegisters gPR = (GeneralPurpousesRegisters) GeneralPurpousesRegisters.getInstance();
	Memory memory = (Memory) Memory.getInstance();
	InstructionRegister iR = (InstructionRegister)InstructionRegister.getInstance();
	StatusRegister sR = (StatusRegister) StatusRegister.getInstance();
	
	@After
	public void reset() {
		memory.resetMemory();
		gPR.resetRegisters();
	}
	
	@Test
	public void movType0test() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				short data = (short) ((i*j)+(i+j));
				
				Register rs1 = new Register(0,0,"R"+i);
				Register rs2 = new Register(0,0,"R"+j);
				
				gPR.setData(j, data);
				
				Mov mov = new Mov(0,0,rs1,rs2,0);
				mov.accept(cU, null);
				
				cU.runInstruction();
				
				assertEquals(data,gPR.getRegisters()[i]);
				
			}
		}
	}
	
	@Test
	public void movType1test() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(i == j)
					continue;
				
				short data = (short) ((i*j)+(i+j));
				short pos = 100;
				
				Register rs1 = new Register(0,0,"R"+i);
				Register rs2 = new Register(0,0,"R"+j);
				
				gPR.setData(j, pos);
				
				
				Mov mov = new Mov(0,0,rs1,rs2,1);
				mov.accept(cU, null);
				
				memory.getMemory()[pos] = data;
				
				cU.runInstruction();
				
				assertEquals(data,gPR.getRegisters()[i]);
				
			}
		}
	}
	
	@Test
	public void movType2test() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(i == j)
					continue;
				
				short data = (short) ((i*j)+(i+j));
				short pos = 100;
				
				Register rs1 = new Register(0,0,"R"+i);
				Register rs2 = new Register(0,0,"R"+j);
				
				gPR.setData(j, data);
				gPR.setData(i, pos);
				
				Mov mov = new Mov(0,0,rs1,rs2,2);
				mov.accept(cU, null);
				
				cU.runInstruction();
				
				assertEquals(data,memory.getMemory()[pos]);
				
			}
		}
	}
	
	@Test
	public void movlTest() {
		for(int i=0;i<8;i++) {
			short data = (short) i;
			
			IntegerLiteral literal = new IntegerLiteral(0,0,data);
			Register register = new Register(0,0,"R"+i);
			
			String movl = String.format(CodeGenerationVisitor.MOVL, register.translate(), literal.translate());
			
			byte lowByte =(byte) (data & 0xFF);
			byte highByte = (byte) ((gPR.getData(i) & 0xFF00) >> 8);
			short res = (short) ((highByte << 8) | (lowByte & 0xFF));
			
			iR.setData( Translate.toDecimalInteger(movl) );
			cU.runInstruction();
			
			assertEquals(res,gPR.getData(i));
		}
	}
	
	@Test
	public void movhTest() {
		for(int i=0;i<8;i++) {
			short data = (short) i;
			
			IntegerLiteral literal = new IntegerLiteral(0,0,data);
			Register register = new Register(0,0,"R"+i);
			
			String movl = String.format(CodeGenerationVisitor.MOVH, register.translate(), literal.translate());
			
			short lowByte = (short) ((gPR.getData(i)) & 0xFF);
			short highByte = (short) ((data & 0xFF) << 8);
			short res = 0;
			res |= lowByte;
			res |= highByte;
			
			iR.setData( Translate.toDecimalInteger(movl) );
			cU.runInstruction();
			
			assertEquals(res,gPR.getData(i));
		}
	}
	
	@Test
	public void pushTest() {
		for(int i=0;i<7;i++) {	
			Register register = new Register(0,0,"R"+i);
			
			Push push = new Push(0,0,register);
			push.accept(cU, null);
			
			gPR.setData(i,(short) i);
			
			int mPosition = Translate.toMemoryPosition( Translate.toBinaryString( gPR.getData(7),16 ) );
			
			cU.runInstruction();
			
			assertEquals(i, memory.getMemory()[mPosition]);
		}
	}
	
	@Test
	public void popTest() {
		for(int i=0;i<7;i++) {
			Register register = new Register(0,0,"R"+i);
			
			Pop pop = new Pop(0,0,register);
			pop.accept(cU, null);
			
			short memPos = (short) (gPR.getData(7)-1);
			gPR.setData(7, memPos);
			
			int rMemPos = Translate.toMemoryPosition( Translate.toBinaryString( gPR.getData(7),16 ) );
			memory.getMemory()[rMemPos] = (short) i;
			
			cU.runInstruction();
			
			assertEquals(i,gPR.getData(i));
		}
	}
	
	@Test
	public void addTest() {
		for(int i=0;i<7;i++) {
			for(int j=0;j<7;j++) {
				for(int w=0;w<7;w++) {
					if(j == w)
						return;
					
					Register rd = new Register(0,0,"R"+i);
					Register rs1 = new Register(0,0,"R"+j);
					Register rs2 = new Register(0,0,"R"+w);
					
					Add add = new Add(0,0,rd,rs1,rs2);
					add.accept(cU, null);
					
					gPR.setData(j,(short) j);
					gPR.setData(w,(short) w);
					
					cU.runInstruction();
					
					int res = j+w;
					
					assertEquals(res, gPR.getData(i));
				}
			}
		}
	}
	
	@Test
	public void subTest() {
		for(int i=0;i<7;i++) {
			for(int j=0;j<7;j++) {
				for(int w=0;w<7;w++) {
					if(j == w)
						return;
					
					Register rd = new Register(0,0,"R"+i);
					Register rs1 = new Register(0,0,"R"+j);
					Register rs2 = new Register(0,0,"R"+w);
					
					Sub sub = new Sub(0,0,rd,rs1,rs2);
					sub.accept(cU, null);
					
					gPR.setData(j,(short) j);
					gPR.setData(w,(short) w);
					
					cU.runInstruction();
					
					int res = j-w;
					
					assertEquals(res, gPR.getData(i));
				}
			}
		}
	}
	
	@Test
	public void orTest() {
		for(int i=0;i<7;i++) {
			for(int j=0;j<7;j++) {
				for(int w=0;w<7;w++) {
					if(j == w)
						return;
					
					Register rd = new Register(0,0,"R"+i);
					Register rs1 = new Register(0,0,"R"+j);
					Register rs2 = new Register(0,0,"R"+w);
					
					Or or = new Or(0,0,rd,rs1,rs2);
					or.accept(cU, null);
					
					gPR.setData(j,(short) j);
					gPR.setData(w,(short) w);
					
					cU.runInstruction();
					
					int res = j|w;
					
					assertEquals(res, gPR.getData(i));
				}
			}
		}
	}
	
	@Test
	public void andTest() {
		for(int i=0;i<7;i++) {
			for(int j=0;j<7;j++) {
				for(int w=0;w<7;w++) {
					if(j == w)
						return;
					
					Register rd = new Register(0,0,"R"+i);
					Register rs1 = new Register(0,0,"R"+j);
					Register rs2 = new Register(0,0,"R"+w);
					
					And and = new And(0,0,rd,rs1,rs2);
					and.accept(cU, null);
					
					gPR.setData(j,(short) j);
					gPR.setData(w,(short) w);
					
					cU.runInstruction();
					
					int res = j&w;
					
					assertEquals(res, gPR.getData(i));
				}
			}
		}
	}
	
	@Test
	public void xorTest() {
		for(int i=0;i<7;i++) {
			for(int j=0;j<7;j++) {
				for(int w=0;w<7;w++) {
					if(j == w)
						return;
					
					Register rd = new Register(0,0,"R"+i);
					Register rs1 = new Register(0,0,"R"+j);
					Register rs2 = new Register(0,0,"R"+w);
					
					Xor xor = new Xor(0,0,rd,rs1,rs2);
					xor.accept(cU, null);
					
					gPR.setData(j,(short) j);
					gPR.setData(w,(short) w);
					
					cU.runInstruction();
					
					int res = j^w;
					
					assertEquals(res, gPR.getData(i));
				}
			}
		}
	}
	
	@Test
	public void cmpTest() {
		for(int i=0;i<7;i++) {
			for(int j=0;j<7;j++) {
				Register rs1 = new Register(0,0,"R"+i);
				Register rs2 = new Register(0,0,"R"+j);
				
				Cmp cmp = new Cmp(0,0,rs1,rs2);
				
				int res = i-j;
				
				cmp.accept(cU, null);
				
				gPR.setData(i, (short) i);
				gPR.setData(j, (short) j);
				
				cU.runInstruction();
				
				int zf = 0, cf = 0, sf = 0;
				
				if(res < 0) {
					cf = 1;
					sf = 1;
					zf = 0;
				}
				
				else if(res > 0) {
					cf = 0;
					sf = 0;
					zf = 0;
				}
				
				else if(res == 0){
					cf = 0;
					sf = 0;
					zf = 1;
				}
				
				assertEquals(zf, sR.getZf());
				assertEquals(cf, sR.getCf());
				assertEquals(sf, sR.getSf());
			}
		}
	}
	
	@Test
	public void notTest() {
		for(int i=0;i<7;i++) {
			Register rsd = new Register(0,0,"R"+i);
			
			Not not = new Not(0,0,rsd);
			
			gPR.setData(i,(short) i);
			not.accept(cU, null);
			
			cU.runInstruction();
			
			assertEquals(~i,gPR.getData(i));
		}
	}
	
	@Test
	public void incTest() {
		for(int i=0;i<7;i++) {
			Register rsd = new Register(0,0,"R"+i);
			
			Inc inc = new Inc(0,0,rsd);
			
			gPR.setData(i,(short) i);
			inc.accept(cU, null);
			
			cU.runInstruction();
			
			int res = i + 1;
			
			assertEquals(res,gPR.getData(i));
		}
	}
	
	@Test
	public void decTest() {
		for(int i=0;i<7;i++) {
			Register rsd = new Register(0,0,"R"+i);
			
			Dec dec = new Dec(0,0,rsd);
			
			gPR.setData(i,(short) i);
			dec.accept(cU, null);
			
			cU.runInstruction();
			
			int res = i - 1;
			
			assertEquals(res,gPR.getData(i));
		}
	}
	
	@Test
	public void negTest() {
		for(int i=0;i<7;i++) {
			Register rsd = new Register(0,0,"R"+i);
			
			Neg neg = new Neg(0,0,rsd);
			
			gPR.setData(i,(short) i);
			neg.accept(cU, null);
			
			cU.runInstruction();
			
			int res = -1 * i;
			
			assertEquals(res,gPR.getData(i));
		}
	}

}
