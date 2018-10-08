package yiplay.tc.memory;

import java.util.ArrayList;
import java.util.List;
import yiplay.tc.AbstractComponent;
import yiplay.tc.memory.bus.AddressBus;
import yiplay.tc.memory.bus.DataBus;
import yiplay.util.Translate;
import yiplay.util.observer.MemoryNotifier;
import yiplay.util.observer.MemoryObserver;

public class Memory extends AbstractComponent implements MemoryNotifier{
		
	public static final int MAX_POS = 65535;
	
	private short[] memory;
	private static AbstractComponent instance;
	private List<MemoryObserver> observers;
	
	private Memory() {
		memory = new short[MAX_POS];
		observers = new ArrayList<>();
		notifyObservers();
	}
	
	public static AbstractComponent getInstance() {
		if(instance == null)
			instance = new Memory();
		return instance;
	}
	
	public void resetMemory() {
		instance = new Memory();
		notifyObservers();
	}
	
	public short[] getMemory() {
		return memory;
	}
	
	public void saveInstructions(int from, List<Short> instructions) {
		int index = 0;
		memory = new short[MAX_POS];
		
		for(int i=from;i<instructions.size();i++) {
			memory[i] = instructions.get(index);
			index++;
		}
		notifyObservers();
	}
	
	public void read() {
		short aB = ((AddressBus)AddressBus.getInstance()).getData();
		String bP = Translate.toBinaryString(aB, 16);
		
		System.out.println(String.format("Read signal launched === Preparing to send %d from %s position to MDR",
				memory[Translate.toMemoryPosition(bP)], bP));
		
		((DataBus)DataBus.getInstance()).setData(memory[ Translate.toMemoryPosition(bP) ]);
	}
	
	public void write() {
		short aB = ((AddressBus)AddressBus.getInstance()).getData();
		String bP = Translate.toBinaryString(aB, 16);
		
		System.out.println(String.format("Write signal launched === Preparing to write %d into %d position",
				((DataBus)DataBus.getInstance()).getData(), Translate.toMemoryPosition(bP)));
		
		memory[ Translate.toMemoryPosition(bP) ] = ((DataBus)DataBus.getInstance()).getData();
		notifyObservers();
	}

	@Override
	public void notifyObservers() {
		for(MemoryObserver ob : observers)
			ob.updateMemory(memory);
	}

	@Override
	public void addObserver(MemoryObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(MemoryObserver observer) {
		observers.remove(observer);
	}
	
}
