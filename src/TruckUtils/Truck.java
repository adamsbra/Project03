package TruckUtils;

import LocationUtils.Location;

import java.awt.*;
import java.time.LocalTime;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;

public class Truck implements Subject {

    private static final int TRAVEL_SPEED = 30;
    private static final int HOUSE_TRAVEL_TIME_SECONDS = (int) (Location.HOUSE_DISTANCE / TRAVEL_SPEED * 60 * 60);
    public static final Location DISTRIBUTION_CENTER = new Location(510, 5, "East", LocalTime.of(8, 1, 0));

    private Location currentLocation;
    private Simulation strategy;
    private int currentStepDuration;
    private Location nextLocation;
    private ArrayList<Observer> observers;
    private LocalTime currentTime;
    public double distance;
    public boolean isMovingEast;
    public boolean isMovingSouth;
    public boolean isMovingWest;
    public boolean isMovingNorth;
    public boolean isMoving;
    public boolean atLocation;


    public Truck(String strategy){
        this.isMovingEast = false;
        this.isMovingSouth = false;
        this.isMovingNorth = false;
        this.isMovingWest = false;
        this.isMoving = false;
        this.atLocation = false;
        this.distance = 0;
        this.currentStepDuration = 1;
        this.currentTime = LocalTime.of(7, 0, 0);
        this.observers = new ArrayList<>();
        this.currentLocation = new Location(510, 5, "East");
        if (strategy.equalsIgnoreCase("left")){
            this.strategy = new LeftSimulation();
        }
        else if (strategy.equalsIgnoreCase("right")){
            this.strategy = new RightSimulation();
        }

    }

    public int getCurrentStepDuration() {
        return currentStepDuration;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalTime currentTime) {
        this.currentTime = currentTime;
    }

    public Location getNextLocation() {
        return nextLocation;
    }

    public boolean isWaiting(Location nextLocation){
        return this.getCurrentTime().compareTo(nextLocation.time) < 0;
    }

    public void setNextLocation(Location nextLocation){
        this.nextLocation = nextLocation;
    }

    public boolean atDistributionCenter(){
        return this.currentLocation == DISTRIBUTION_CENTER;
    }

    public Location getDistributionCenter(){
        return DISTRIBUTION_CENTER;
    }

    //gets the next location and get the distance from previous to next and add it to length. Also changes the current location of the truck.
    //That way we don't have to go through the PriorityQueue twice(since we don't know another way to get the length).

    public void addDistance(){
        this.distance += Location.HOUSE_DISTANCE;
    }

    //returns current location of the truck
    public Location getLocation() {
        return currentLocation;
    }

    public void setLocation(){
        int east = this.getLocation().east;
        int south = this.getLocation().south;
        if (isMoving){
            String direction = "NA";
            int streetNumber = 0;
            int houseNumber = 0;
            if (isMovingEast || isMovingWest){
                direction = "East";
                streetNumber = (south + 10) / 10;
                houseNumber = east * 10;
            }
            else if (isMovingNorth || isMovingSouth){
                direction = "South";
                streetNumber = (east + 10) / 10;
                houseNumber = south * 10;
            }
            currentLocation.direction = direction;
            currentLocation.street_number = streetNumber;
            currentLocation.house_number = houseNumber;
        }
        else{
            int streetNumber = 0;
            int houseNumber = 0;
            String direction = currentLocation.direction;
            if (direction.equalsIgnoreCase("east")){
                streetNumber = (south + 10) / 10;
                houseNumber = east * 10;
            }
            else if (direction.equalsIgnoreCase("south")){
                streetNumber = (east + 10) / 10;
                houseNumber = south * 10;
            }
            currentLocation.street_number = streetNumber;
            currentLocation.house_number = houseNumber;
        }
    }

    public void moveEast(){
        currentLocation.east++;
        setLocation();
        resetBooleans();
        isMovingEast = true;
    }

    public void moveWest(){
        currentLocation.east--;
        setLocation();
        resetBooleans();
        isMovingWest = true;
    }

    public void moveNorth(){
        currentLocation.south--;
        setLocation();
        resetBooleans();
        isMovingNorth = true;
    }

    public void moveSouth(){
        currentLocation.south++;
        setLocation();
        resetBooleans();
        isMovingSouth = true;
    }

    public void resetBooleans(){
        isMovingEast = false;
        isMovingSouth = false;
        isMovingWest = false;
        isMovingNorth = false;
    }

    public boolean atIntersection(){
        return currentLocation.east % 10 == 0 && currentLocation.south % 10 == 0;
    }

    public void setCurrentLocation(Location location){
        this.currentLocation = location;
    }

    public void setCurrentStepDuration(int duration){
        this.currentStepDuration = duration;
    }

    public void notifyObservers(){
        for (Observer observer: observers){
            notifyObserver(observer);
        }
    }

    @Override
    public void notifyObserver(Observer observer){
        observer.update(this);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    public void update(){
        notifyObservers();
    }

    public void updateCurrentTime(){
        currentTime = currentTime.plus(currentStepDuration * HOUSE_TRAVEL_TIME_SECONDS, ChronoUnit.SECONDS);
    }

    public void addCurrentTime(int seconds){
        currentTime = currentTime.plus(seconds, ChronoUnit.SECONDS);
    }

    public void setAtLocation(boolean atLocation){
        this.atLocation = atLocation;
    }

    public ArrayList<Point> runStrategy(TruckTracker tracker){
        try {
            return strategy.runSimulation(this, tracker);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}