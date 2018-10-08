package yiplay.util.observer;

public interface IBNotifier {

	void notifyObservers();
	void addObserver(IBObserver observer);
	void removeObserver(IBObserver observer);
	
}
