package TruckUtils;

public interface Subject {

    public void notifyObserver(Observer observer);
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
}
