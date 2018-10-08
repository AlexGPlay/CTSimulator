package yiplay.tc.cpu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import yiplay.language.ast.Program;
import yiplay.language.ast.expression.BinaryLiteral;
import yiplay.language.ast.expression.HexLiteral;
import yiplay.language.ast.expression.IntegerLiteral;
import yiplay.language.ast.expression.Register;
import yiplay.language.ast.statement.Add;
import yiplay.language.ast.statement.And;
import yiplay.language.ast.statement.Brc;
import yiplay.language.ast.statement.Brnc;
import yiplay.language.ast.statement.Brno;
import yiplay.language.ast.statement.Brns;
import yiplay.language.ast.statement.Brnz;
import yiplay.language.ast.statement.Bro;
import yiplay.language.ast.statement.Brs;
import yiplay.language.ast.statement.Brz;
import yiplay.language.ast.statement.Call;
import yiplay.language.ast.statement.Cli;
import yiplay.language.ast.statement.Cmp;
import yiplay.language.ast.statement.Dec;
import yiplay.language.ast.statement.Halt;
import yiplay.language.ast.statement.Inc;
import yiplay.language.ast.statement.Int;
import yiplay.language.ast.statement.Iret;
import yiplay.language.ast.statement.Jmp;
import yiplay.language.ast.statement.Label;
import yiplay.language.ast.statement.Mov;
import yiplay.language.ast.statement.Movh;
import yiplay.language.ast.statement.Movl;
import yiplay.language.ast.statement.Neg;
import yiplay.language.ast.statement.Nop;
import yiplay.language.ast.statement.Not;
import yiplay.language.ast.statement.Or;
import yiplay.language.ast.statement.Pop;
import yiplay.language.ast.statement.Push;
import yiplay.language.ast.statement.Ret;
import yiplay.language.ast.statement.Sti;
import yiplay.language.ast.statement.Sub;
import yiplay.language.ast.statement.Xor;
import yiplay.language.visitor.Visitor;
import yiplay.tc.AbstractComponent;
import yiplay.tc.cpu.bus.InternalBus;
import yiplay.tc.cpu.register.GeneralPurpousesRegisters;
import yiplay.tc.cpu.register.InstructionRegister;
import yiplay.tc.cpu.register.MemoryDataRegister;
import yiplay.tc.cpu.register.ProgramCounter;
import yiplay.tc.cpu.register.StatusRegister;
import yiplay.tc.cpu.register.TMPE;
import yiplay.tc.cpu.register.TMPS;
import yiplay.tc.memory.Memory;

public class ControlUnit extends AbstractComponent implements Visitor{
		
	public static final int NOP_CYCLES = 1;
	public static final int MOV_RD_RS_CYCLES = 1;
	public static final int MOV_RD_RI_CYCLES = 3;
	public static final int MOV_RI_RS_CYCLES = 3;
	public static final int MOVL_CYCLES = 1;
	public static final int MOVH_CYCLES = 1;
	public static final int PUSH_CYCLES = 4;
	public static final int POP_CYCLES = 3;
	public static final int ADD_CYCLES = 3;
	public static final int SUB_CYCLES = 3;
	public static final int OR_CYCLES = 3;
	public static final int AND_CYCLES = 3;
	public static final int XOR_CYCLES = 3;
	public static final int CMP_CYCLES = 2;
	public static final int NOT_CYCLES = 2;
	public static final int INC_CYCLES = 2;
	public static final int DEC_CYCLES = 2;
	public static final int NEG_CYCLES = 2;
	public static final int CLI_CYCLES = 1;
	public static final int STI_CYCLES = 1;
	public static final int INT_CYCLES = 10;
	public static final int IRET_CYCLES = 6;
	public static final int JMP_INM8_CYCLES = 3;
	public static final int JMP_RX_CYCLES = 1;
	public static final int CALL_INM8_CYCLES = 5;
	public static final int CALL_RX_CYCLES = 4;
	public static final int RET_CYCLES = 3;
	public static final int BR_TRUE_CYCLES = 3;
	public static final int BR_FALSE_CYCLES = 1;
	public static final int HALT_CYCLES = 1;
	public static final int BASIC_CYCLES = 3;
	
	private static AbstractComponent instance;
	
	private List<Consumer<Void>>[] cycles;
	private int actualCycle;
	private int totalCycles;
	private boolean finishedInstruction;
	private boolean finishedExecution;
	
	private ControlUnit() {
		actualCycle = 0;
		totalCycles = 0;
		finishedExecution = false;
		Fin();
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new ControlUnit();
		return instance;
	}
	
	public void setActualCycle(int cycle) {
		this.actualCycle = cycle;
	}
	
	public List<Consumer<Void>>[] getCycles(){
		return cycles;
	}
	
	public boolean isExecutionFinished() {
		return finishedExecution;
	}
	
	public int getTotalCycles() {
		return totalCycles;
	}
	
	public void runAll() {
		while(!finishedExecution)
			runInstruction();
	}
	
	public void runInstruction() {
		finishedInstruction = false;
	
		while(!finishedInstruction)
			runCycle();
	}
	
	public void runCycle() {
		int rcCycle = actualCycle;
		
		if(actualCycle >= 3)
			rcCycle = actualCycle-3;
		
		List<Consumer<Void>> toRun = cycles[rcCycle];
		
		for(Consumer<Void> cS : toRun)
			cS.accept(null);
		
		if(!finishedInstruction)
			actualCycle++;
		totalCycles++;
	}
	
	public void Fin() {
		basicCycle();
		actualCycle = 0;
		finishedInstruction = true;
		System.out.println("Fin signal launched");
	}
	
	public void FinExecution() {
		actualCycle = 0;
		finishedInstruction = true;
		finishedExecution = true;
		System.out.println("Exeuction end signal launched");
	}
	
	@SuppressWarnings("unchecked")
	public void basicCycle() {
		cycles = (List<Consumer<Void>>[])new List[BASIC_CYCLES];
		System.out.println("------------ Preparing next instruction ------------");
		initializeArray();
		
		finishedInstruction = false;
		
		cycles[0].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );
		cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Clr() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Carry_In() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		cycles[0].add( (Void) -> ((Memory)Memory.getInstance()).read() );
		cycles[1].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );
		cycles[2].add( (Void) -> ((MemoryDataRegister)MemoryDataRegister.getInstance()).Mdr_Ib() );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Ir() );
	}
	
	public void initializeArray() {
		for(int i=0;i<cycles.length;i++)
			cycles[i] = new ArrayList<Consumer<Void>>();
	}

	@Override
	public Object visit(Program ast, Object param) {
		return null;
	}

	@Override
	public Object visit(BinaryLiteral ast, Object param) {
		return null;
	}

	@Override
	public Object visit(HexLiteral ast, Object param) {
		return null;
	}

	@Override
	public Object visit(IntegerLiteral ast, Object param) {
		return null;
	}

	@Override
	public Object visit(Register ast, Object param) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Add ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[ADD_CYCLES];
		initializeArray();

		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs1()).getRegister() ));
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
		
		cycles[1].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs2()).getRegister() ));
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Sr() );
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx( ((Register)ast.getRd()).getRegister() ) );
		cycles[2].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(And ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[AND_CYCLES];
		initializeArray();

		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs1()).getRegister() ));
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
		
		cycles[1].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs2()).getRegister() ));
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).And() );
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Sr() );
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx( ((Register)ast.getRd()).getRegister() ) );
		cycles[2].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Brc ast, Object param) {
		int nBit = ((StatusRegister)StatusRegister.getInstance()).getCf();
		
		if(nBit == 1) {
			cycles = (List<Consumer<Void>>[])new List[BR_TRUE_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );	
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
			
			cycles[1].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).ExtIrl_Ib() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
			
			cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );	
			cycles[2].add( (Void) -> Fin() );
		}
		
		else if(nBit == 0) {
			cycles = (List<Consumer<Void>>[])new List[BR_FALSE_CYCLES];
			initializeArray();
			cycles[0].add( (Void) -> Fin() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Brnc ast, Object param) {
		int nBit = ((StatusRegister)StatusRegister.getInstance()).getCf();
		
		if(nBit == 0) {
			cycles = (List<Consumer<Void>>[])new List[BR_TRUE_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );	
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
			
			cycles[1].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).ExtIrl_Ib() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
			
			cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );	
			cycles[2].add( (Void) -> Fin() );
		}
		
		else if(nBit == 1) {
			cycles = (List<Consumer<Void>>[])new List[BR_FALSE_CYCLES];
			initializeArray();
			cycles[0].add( (Void) -> Fin() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Brno ast, Object param) {
		int nBit = ((StatusRegister)StatusRegister.getInstance()).getOf();
		
		if(nBit == 0) {
			cycles = (List<Consumer<Void>>[])new List[BR_TRUE_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );	
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
			
			cycles[1].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).ExtIrl_Ib() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
			
			cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );	
			cycles[2].add( (Void) -> Fin() );
		}
		
		else if(nBit == 1) {
			cycles = (List<Consumer<Void>>[])new List[BR_FALSE_CYCLES];
			initializeArray();
			cycles[0].add( (Void) -> Fin() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Brns ast, Object param) {
		int nBit = ((StatusRegister)StatusRegister.getInstance()).getSf();
		
		if(nBit == 0) {
			cycles = (List<Consumer<Void>>[])new List[BR_TRUE_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );	
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
			
			cycles[1].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).ExtIrl_Ib() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
			
			cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );	
			cycles[2].add( (Void) -> Fin() );
		}
		
		else if(nBit == 1) {
			cycles = (List<Consumer<Void>>[])new List[BR_FALSE_CYCLES];
			initializeArray();
			cycles[0].add( (Void) -> Fin() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Brnz ast, Object param) {
		int nBit = ((StatusRegister)StatusRegister.getInstance()).getZf();
		
		if(nBit == 0) {
			cycles = (List<Consumer<Void>>[])new List[BR_TRUE_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );	
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
			
			cycles[1].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).ExtIrl_Ib() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
			
			cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );	
			cycles[2].add( (Void) -> Fin() );
		}
		
		else if(nBit == 1) {
			cycles = (List<Consumer<Void>>[])new List[BR_FALSE_CYCLES];
			initializeArray();
			cycles[0].add( (Void) -> Fin() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Bro ast, Object param) {
		int nBit = ((StatusRegister)StatusRegister.getInstance()).getOf();
		
		if(nBit == 1) {
			cycles = (List<Consumer<Void>>[])new List[BR_TRUE_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );	
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
			
			cycles[1].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).ExtIrl_Ib() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
			
			cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );	
			cycles[2].add( (Void) -> Fin() );
		}
		
		else if(nBit == 0) {
			cycles = (List<Consumer<Void>>[])new List[BR_FALSE_CYCLES];
			initializeArray();
			cycles[0].add( (Void) -> Fin() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Brs ast, Object param) {
		int nBit = ((StatusRegister)StatusRegister.getInstance()).getSf();
		
		if(nBit == 1) {
			cycles = (List<Consumer<Void>>[])new List[BR_TRUE_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );	
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
			
			cycles[1].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).ExtIrl_Ib() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
			
			cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );	
			cycles[2].add( (Void) -> Fin() );
		}
		
		else if(nBit == 0) {
			cycles = (List<Consumer<Void>>[])new List[BR_FALSE_CYCLES];
			initializeArray();
			cycles[0].add( (Void) -> Fin() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Brz ast, Object param) {
		int nBit = ((StatusRegister)StatusRegister.getInstance()).getZf();
		
		if(nBit == 1) {
			cycles = (List<Consumer<Void>>[])new List[BR_TRUE_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );	
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
			
			cycles[1].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).ExtIrl_Ib() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
			
			cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );	
			cycles[2].add( (Void) -> Fin() );
		}
		
		else if(nBit == 0) {
			cycles = (List<Consumer<Void>>[])new List[BR_FALSE_CYCLES];
			initializeArray();
			cycles[0].add( (Void) -> Fin() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Call ast, Object param) {
		if(ast.getMode() == 0) {
			cycles = (List<Consumer<Void>>[])new List[CALL_INM8_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib(7));
			cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Set() );
			cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );

			cycles[1].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );
			cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mdr() );		
			cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );		

			cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx(7));
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );	
			cycles[2].add( (Void) -> ((Memory)Memory.getInstance()).write() );
			
			cycles[3].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).ExtIrl_Ib() );
			cycles[3].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[3].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
			
			cycles[4].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[4].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );	
			cycles[4].add( (Void) -> Fin() );
		}
		
		else if(ast.getMode() == 1) {
			cycles = (List<Consumer<Void>>[])new List[CALL_RX_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib(7));
			cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Set() );
			cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );

			cycles[1].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );
			cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mdr() );	
			
			cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx(7));
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );	
			cycles[2].add( (Void) -> ((Memory)Memory.getInstance()).write() );
			
			cycles[3].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getLines()).getRegister() ));
			cycles[3].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );	
			cycles[3].add( (Void) -> Fin() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Cli ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[CLI_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((StatusRegister)StatusRegister.getInstance()).Cli() );
		cycles[0].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Cmp ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[CMP_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs1()).getRegister() ));
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );		
		
		cycles[1].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs2()).getRegister() ));
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Sub() );
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Sr() );
		cycles[1].add( (Void) -> Fin() );
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Halt ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[HALT_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> FinExecution() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Dec ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[DEC_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRsd()).getRegister() ));
		cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Set() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Sr() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[1].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx( ((Register)ast.getRsd()).getRegister() ) );
		cycles[1].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Inc ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[INC_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRsd()).getRegister() ));
		cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Clr() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Carry_In() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Sr() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[1].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx( ((Register)ast.getRsd()).getRegister() ) );
		cycles[1].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Int ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[INT_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib(7));
		cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Set() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[1].add( (Void) -> ((StatusRegister)StatusRegister.getInstance()).Sr_Ib() );
		cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mdr() );
		
		cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx(7) );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );
		cycles[2].add( (Void) -> ((Memory)Memory.getInstance()).write() );
		
		cycles[3].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib(7));
		cycles[3].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Set() );
		cycles[3].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
		cycles[3].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[4].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );
		cycles[4].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mdr() );
		
		cycles[5].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[5].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx(7) );
		cycles[5].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );
		cycles[5].add( (Void) -> ((Memory)Memory.getInstance()).write() );
		
		cycles[6].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).ExtIrl_Ib() );
		cycles[6].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );
		cycles[6].add( (Void) -> ((Memory)Memory.getInstance()).read() );
		
		cycles[7].add( (Void) -> ((MemoryDataRegister)MemoryDataRegister.getInstance()).Mdr_Ib() );
		cycles[7].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );
		cycles[7].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Iret ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[IRET_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib(7));
		cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Clr() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Carry_In() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );
		cycles[0].add( (Void) -> ((Memory)Memory.getInstance()).read() );
		
		cycles[1].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx(7) );
		
		cycles[2].add( (Void) -> ((MemoryDataRegister)MemoryDataRegister.getInstance()).Mdr_Ib() );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );
		
		cycles[3].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib(7));
		cycles[3].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Clr() );
		cycles[3].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Carry_In() );
		cycles[3].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
		cycles[3].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		cycles[3].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );
		cycles[3].add( (Void) -> ((Memory)Memory.getInstance()).read() );
		
		cycles[4].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[4].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx(7) );
		
		cycles[5].add( (Void) -> ((MemoryDataRegister)MemoryDataRegister.getInstance()).Mdr_Ib() );
		cycles[5].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Sr() );
		cycles[5].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Jmp ast, Object param) {
		if(ast.getMode() == 0) {
			cycles = (List<Consumer<Void>>[])new List[JMP_INM8_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((ProgramCounter)ProgramCounter.getInstance()).Pc_Ib() );
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
			
			cycles[1].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).ExtIrl_Ib() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
			cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
			
			cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );
			cycles[2].add( (Void) -> Fin() );
		}
		
		else if(ast.getMode() == 1) {
			cycles = (List<Consumer<Void>>[])new List[JMP_RX_CYCLES];
			initializeArray();
			cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getLines()).getRegister() ));
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );
			cycles[0].add( (Void) -> Fin() );
		}
		
		return null;
	}

	@Override
	public Object visit(Label ast, Object param) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Mov ast, Object param) {
		if(ast.getType() == 0) {
			cycles = (List<Consumer<Void>>[])new List[MOV_RD_RS_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs()).getRegister() ));
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx(((Register)ast.getRd()).getRegister() ) );
			cycles[0].add( (Void) -> Fin() );
		}
		
		else if(ast.getType() == 1) {
			cycles = (List<Consumer<Void>>[])new List[MOV_RD_RI_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs()).getRegister() ));
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );
			cycles[0].add( (Void) -> ((Memory)Memory.getInstance()).read() );
			
			cycles[2].add( (Void) -> ((MemoryDataRegister)MemoryDataRegister.getInstance()).Mdr_Ib() );
			cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx( ((Register)ast.getRd()).getRegister() ) );
			cycles[2].add( (Void) -> Fin() );
		}
		
		else if(ast.getType() == 2) {
			cycles = (List<Consumer<Void>>[])new List[MOV_RI_RS_CYCLES];
			initializeArray();
			
			cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRd()).getRegister() ));
			cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );
			
			cycles[1].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs()).getRegister() ));
			cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mdr() );
			cycles[1].add( (Void) -> ((Memory)Memory.getInstance()).write() );
			
			cycles[2].add( (Void) -> Fin() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Movh ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[MOVL_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).Irl_Ibh() );
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ibh_Rxh( ((Register)ast.getRegister()).getRegister() ) );
		cycles[0].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Movl ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[MOVH_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((InstructionRegister)InstructionRegister.getInstance()).Irl_Ibl() );
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ibl_Rxl( ((Register)ast.getRegister()).getRegister() ) );
		cycles[0].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Neg ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[NEG_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRsd()).getRegister() ));
		cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Clr() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Sub() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Sr() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[1].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx( ((Register)ast.getRsd()).getRegister() ) );
		cycles[1].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Nop ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[NOP_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Not ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[NOT_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRsd()).getRegister() ));
		cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Set() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Xor() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Sr() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[1].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx( ((Register)ast.getRsd()).getRegister() ) );
		cycles[1].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Or ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[OR_CYCLES];
		initializeArray();

		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs1()).getRegister() ));
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
		
		cycles[1].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs2()).getRegister() ));
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Or() );
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Sr() );
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx( ((Register)ast.getRd()).getRegister() ) );
		cycles[2].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Pop ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[POP_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib(7));
		cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Clr() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Carry_In() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );
		cycles[0].add( (Void) -> ((Memory)Memory.getInstance()).read() );
		
		cycles[1].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx(7) );
		
		cycles[2].add( (Void) -> ((MemoryDataRegister)MemoryDataRegister.getInstance()).Mdr_Ib() );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx( ((Register)ast.getRegister()).getRegister() ) );
		cycles[2].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Push ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[PUSH_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib(7));
		cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Set() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[1].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRegister()).getRegister() ));
		cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mdr() );
		
		cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx(7) );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );
		cycles[2].add( (Void) -> ((Memory)Memory.getInstance()).write() );
		
		cycles[3].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Ret ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[RET_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib(7));
		cycles[0].add( (Void) -> ((TMPE)TMPE.getInstance()).Tmpe_Clr() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Carry_In() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Add() );
		cycles[0].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Mar() );
		cycles[0].add( (Void) -> ((Memory)Memory.getInstance()).read() );

		cycles[1].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[1].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx(7) );

		cycles[2].add( (Void) -> ((MemoryDataRegister)MemoryDataRegister.getInstance()).Mdr_Ib() );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Pc() );
		cycles[2].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Sti ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[CLI_CYCLES];
		initializeArray();
		
		cycles[0].add( (Void) -> ((StatusRegister)StatusRegister.getInstance()).Sti() );
		cycles[0].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Sub ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[SUB_CYCLES];
		initializeArray();

		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs1()).getRegister() ));
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
		
		cycles[1].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs2()).getRegister() ));
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Sub() );
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Sr() );
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx( ((Register)ast.getRd()).getRegister() ) );
		cycles[2].add( (Void) -> Fin() );
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object visit(Xor ast, Object param) {
		cycles = (List<Consumer<Void>>[])new List[XOR_CYCLES];
		initializeArray();

		cycles[0].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs1()).getRegister() ));
		cycles[0].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Tmpe() );
		
		cycles[1].add( (Void) -> ((GeneralPurpousesRegisters)GeneralPurpousesRegisters.getInstance()).Rx_Ib( ((Register)ast.getRs2()).getRegister() ));
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Xor() );
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Sr() );
		cycles[1].add( (Void) -> ((ArithmeticLogicUnit)ArithmeticLogicUnit.getInstance()).Alu_Tmps() );
		
		cycles[2].add( (Void) -> ((TMPS)TMPS.getInstance()).Tmps_Ib() );
		cycles[2].add( (Void) -> ((InternalBus)InternalBus.getInstance()).Ib_Rx( ((Register)ast.getRd()).getRegister() ) );
		cycles[2].add( (Void) -> Fin() );
		
		return null;
	}

}
