package yiplay.util.observer;

public interface GPRNotifier {

	void notifyObservers(int register, short value);
	void addObserver(GPRObserver observer);
	void removeObserver(GPRObserver observer);
	
}
