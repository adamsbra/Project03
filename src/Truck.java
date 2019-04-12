import java.time.LocalTime;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Truck implements TruckSubject{

    private final Location DISTRIBUTION_CENTER = new Location(510, 5, "East");

    private Location currentLocation = DISTRIBUTION_CENTER;
    int currentDuration = 1;
    public Location nextLocation;
    public PriorityQueue<Location> locations;
    public boolean isMovingEast = false;
    public boolean isMovingSouth = false;
    public boolean isMovingWest = false;
    public boolean isMovingNorth = false;
    public boolean isMoving = false;
    public Observer gui;
    Map<Integer, Integer> route = new HashMap<>();
    double length = 0;

    //Route the truck will follow
    public void setRoute(Map<Integer, Integer> route) {
        this.route = route;
    }


    public void setNextLocation(Location nextLocation){
        this.nextLocation = nextLocation;
    }

    public void setLocations(PriorityQueue<Location> locations){
        this.locations = locations;
    }
    //gets the next location and get the distance from previous to next and add it to length. Also changes the current location of the truck.
    //That way we don't have to go through the PriorityQueue twice(since we don't know another way to get the length).

    //This somewhat works, but remember that distance will change because we cannot go backwards on roads.
//    private void nextLocation(){
//        for(int i = 0; i < 100; i++){
//            Location nextLocation = route.poll();
//            length += nextLocation.getDistance(currentLocation);
//            currentLocation = nextLocation;
//        }
//    }
    //returns current location of the truck
    public Location getLocation() {
        return currentLocation;
    }

    //these are really confusing, it increments south for move east because technically incrementing south houses means
    //you're moving east
    public void moveEast(){
        currentLocation.east++;
        resetBooleans();
        isMovingEast = true;
    }

    public void moveWest(){
        currentLocation.east--;
        resetBooleans();
        isMovingWest = true;
    }

    public void moveNorth(){
        currentLocation.south--;
        resetBooleans();
        isMovingNorth = true;
    }

    public void moveSouth(){
        currentLocation.south++;
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

    public void setCurrentDuration(int duration){
        this.currentDuration = duration;
    }
    //Not currently implemented using a list, since we only have the need for a single gui.
    @Override
    public void notifyObserver(Observer observer){
        observer.updateGui(this.currentLocation, this.nextLocation, this.currentDuration, this.locations);
        // observer.updateTracker(this.currentLocation, blah, blah);
    }

    @Override
    public void registerObserver(Observer observer) {
        this.gui = observer;
    }

    @Override
    public void removeObserver(Observer observer) {
        this.gui = null;
    }

    public void updateGui(){
        notifyObserver(gui);
    }
}