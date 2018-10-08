package yiplay.util.observer;

public interface GPRNotifier {

	void notifyObservers();
	void addObserver(GPRObserver observer);
	void removeObserver(GPRObserver observer);
	
}
