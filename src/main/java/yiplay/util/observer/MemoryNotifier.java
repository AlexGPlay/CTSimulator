package yiplay.util.observer;

public interface MemoryNotifier {

	void notifyObservers();
	void addObserver(MemoryObserver observer);
	void removeObserver(MemoryObserver observer);
	
}
