public interface Subject {
    public void notifyObservers();
    public void registerObservers(Observer observer);
    public void removeObservers(Observer observer);
}
