/*
Author : Team Null
Interface that has runSimulation
 */

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;

public interface Simulation {
    //5280ft 1mile
    double BLOCK_DISTANCE = 1320;
    //    double HOUSE_DISTANCE = BLOCK_DISTANCE / 9;
    double HOUSE_DISTANCE = 0.03 * 5280; //0.03 miles
    int MILES_PER_HOUR = 30; //truck's velocity
    public Route runSimulation(LocationMapDisplay lmd, PriorityQueue<Location> locations, Truck truck, boolean gui) throws InterruptedException;
}

//Strategy for the truck to only turn left.
class LeftSimulation implements Simulation, Subject{

    private ArrayList<Observer> observers;
    private OrderPrinter orderPrinter;
    private Location nextLocation;
    private Location truckLocation;
    private Route route;
    private LocationMapDisplay lmd;
    private ArrayList<Pair<Integer, Integer>> locationsArray;
    private int duration = 0;
    private int distance = 0;
    private final int STEP_TIME = 200;
    private final int STOP_UNITS = 5;
    private final int MOVE_UNITS = 1;
    private final int LEFT_UNITS = 4;

    //Implements strategy for the truck to only make left turns
    @Override
    public Route runSimulation(LocationMapDisplay loc_md, PriorityQueue<Location> locations, Truck truck, boolean gui) throws InterruptedException {
        this.lmd = loc_md;
        this.locationsArray = new ArrayList<>();
        PriorityQueue<Location> locations_copy = locations;
        while (locations_copy.size() != 0){
            nextLocation = locations_copy.poll();
            lmd.nextLocation = nextLocation;
            lmd.truck = truck;
            while (truck.getLocation().east != nextLocation.east || truck.getLocation().south != nextLocation.south) {
                int current_duration = 0;
                if (!truck.isMoving) {
                    if (truck.getLocation().direction.equalsIgnoreCase("east")) {
                        int east = nextLocation.east - truck.getLocation().east;
                        if (east > 0) {
                            truck.moveEast();
                        }
                        else if (east < 0) {
                            truck.moveWest();
                        }
                    } else if (truck.getLocation().direction.equalsIgnoreCase("south")) {
                        int south = nextLocation.south - truck.getLocation().south;
                        if (south > 0) {
                            truck.moveSouth();
                        }
                        else if (south < 0) {
                            truck.moveNorth();
                        }
                    }
                    truck.isMoving = true;
                    current_duration = MOVE_UNITS;
                    truckLocation = truck.getLocation();
                    Thread.sleep(current_duration * STEP_TIME);
                }
                else if (!truck.atIntersection()){
                    if (truck.isMovingEast){
                        truck.moveEast();
                    }
                    else if (truck.isMovingWest){
                        truck.moveWest();
                    }
                    else if (truck.isMovingNorth){
                        truck.moveNorth();
                    }
                    else if (truck.isMovingSouth){
                        truck.moveSouth();
                    }
                    truckLocation = truck.getLocation();
                    current_duration = MOVE_UNITS;
                }
                else if (truck.atIntersection()){
                    int south = nextLocation.south - truck.getLocation().south;
                    int east = nextLocation.east - truck.getLocation().east;
                    if (truck.isMovingEast){
                        if (east > 0){
                            current_duration = MOVE_UNITS;
                            truck.moveEast();
                        }
                        else {
                            current_duration = LEFT_UNITS;
                            truck.moveNorth();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingSouth){
                        if (south > 0){
                            current_duration = MOVE_UNITS;
                            truck.moveSouth();
                        }
                        else {
                            current_duration = LEFT_UNITS;
                            truck.moveEast();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingNorth){
                        if (south < 0){
                            current_duration = MOVE_UNITS;
                            truck.moveNorth();
                        }
                        else {
                            current_duration = LEFT_UNITS;
                            truck.moveWest();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingWest){
                        if (east < 0){
                            current_duration = MOVE_UNITS;
                            truck.moveWest();
                        }
                        else {
                            current_duration = LEFT_UNITS;
                            truck.moveSouth();
                        }
                        truckLocation = truck.getLocation();
                    }
                }
                distance++;
                duration += current_duration;
                if (gui) {
                    Thread.sleep(STEP_TIME * current_duration);
                    lmd.repaint();
                }
            }
            truckLocation = truck.getLocation();
            lmd.map[nextLocation.east][nextLocation.south] = "";
            duration += STOP_UNITS;
            if (gui) {
                Thread.sleep(STEP_TIME * STOP_UNITS);
                //lmd.repaint();
                orderPrinter = new OrderPrinter(duration, distance, HOUSE_DISTANCE, MILES_PER_HOUR);// Prints the duration and distance
                notifyObservers();
//                duration = 0; //to get time from one house to the other instead of overall time.
            }
            locationsArray.add(new Pair(truckLocation.east, truckLocation.south));
        }

        new OrderPrinter(duration, distance, HOUSE_DISTANCE, MILES_PER_HOUR);// Prints the duration and distance
        route = new Route (locationsArray, duration, distance);
        return route;
    }

    @Override
    public void notifyObservers() {
        orderPrinter.update(nextLocation);
        lmd.update(nextLocation);
    }

    @Override
    public void registerObservers(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObservers(Observer observer) {
        for(int i = 0; i < observers.size(); i++){
            if(observer == observers.get(i)) {
                observers.remove(i);
                break;
            }
        }
    }
}

//Strategy for the truck to only turn right.
class RightSimulation implements Simulation, Subject{

    private ArrayList<Observer> observers;
    private OrderPrinter orderPrinter;
    private Location nextLocation;
    private Location truckLocation;
    private int duration = 0;
    private int distance = 0;
    private Route route;
    private LocationMapDisplay lmd;
    private ArrayList<Pair<Integer, Integer>> locationsArray;
    private final int STEP_TIME = 200;
    private final int STOP_UNITS = 5;
    private final int MOVE_UNITS = 1;
    private final int RIGHT_UNITS = 4;

    //Implements strategy for the truck to only make right turns
    @Override
    public Route runSimulation(LocationMapDisplay lmd, PriorityQueue<Location> locations, Truck truck, boolean gui) throws InterruptedException {
        PriorityQueue<Location> locations_copy = locations;
        locationsArray = new ArrayList<>();
        while (locations_copy.size() != 0) {
            nextLocation = locations_copy.poll();
            lmd.nextLocation = nextLocation;
            lmd.truck = truck;
            this.lmd = lmd;
            while (truck.getLocation().east != nextLocation.east || truck.getLocation().south != nextLocation.south) {
                int current_duration = 0;
                if (!truck.isMoving) {
                    if (truck.getLocation().direction.equalsIgnoreCase("east")) {
                        int east = nextLocation.east - truck.getLocation().east;
                        if (east > 0) {
                            truck.moveEast();
                        } else {
                            truck.moveWest();
                        }


                    } else if (truck.getLocation().direction.equalsIgnoreCase("south")) {
                        int south = nextLocation.south - truck.getLocation().south;
                        if (south > 0) {
                            truck.moveSouth();
                        } else {
                            truck.moveNorth();
                        }
                    }
                    truck.isMoving = true;
                    truckLocation = truck.getLocation();
                    current_duration = MOVE_UNITS;
                } else if (!truck.atIntersection()) {
                    if (truck.isMovingEast) {
                        truck.moveEast();
                    } else if (truck.isMovingWest) {
                        truck.moveWest();
                    } else if (truck.isMovingNorth) {
                        truck.moveNorth();
                    } else if (truck.isMovingSouth) {
                        truck.moveSouth();
                    }
                    truckLocation = truck.getLocation();
                    current_duration = MOVE_UNITS;

                } else if (truck.atIntersection()) {
                    int south = nextLocation.south - truck.getLocation().south;
                    int east = nextLocation.east - truck.getLocation().east;
                    if (truck.isMovingEast) {
                        if (east > 0) {
                            current_duration = MOVE_UNITS;
                            truck.moveEast();
                        } else {
                            current_duration = RIGHT_UNITS;
                            truck.moveSouth();
                        }
                        truckLocation = truck.getLocation();
                    } else if (truck.isMovingSouth) {
                        if (south > 0) {
                            current_duration = MOVE_UNITS;
                            truck.moveSouth();
                        } else {
                            current_duration = RIGHT_UNITS;
                            truck.moveWest();
                        }
                        truckLocation = truck.getLocation();
                    } else if (truck.isMovingNorth) {
                        if (south < 0) {
                            current_duration = MOVE_UNITS;
                            truck.moveNorth();
                        } else {
                            current_duration = RIGHT_UNITS;
                            truck.moveEast();
                        }
                        truckLocation = truck.getLocation();
                    } else if (truck.isMovingWest) {
                        if (east < 0) {
                            truck.moveWest();
                            current_duration = MOVE_UNITS;
                        } else {
                            current_duration = RIGHT_UNITS;
                            truck.moveNorth();
                        }
                        truckLocation = truck.getLocation();
                    }
                }
                distance++;
                duration += current_duration;
                if (gui) {
                    Thread.sleep(STEP_TIME * current_duration);
                    lmd.repaint();
                }
            }
            truckLocation = truck.getLocation();
            lmd.map[nextLocation.east][nextLocation.south] = "";
            duration += STOP_UNITS;
            if (gui) {
                Thread.sleep(STEP_TIME * STOP_UNITS);
                //lmd.repaint();
                orderPrinter = new OrderPrinter(duration, distance, HOUSE_DISTANCE, MILES_PER_HOUR);// Prints the duration and distance
                notifyObservers();

            }
            locationsArray.add(new Pair(truckLocation.east, truckLocation.south));
        }
        new OrderPrinter(duration, distance, HOUSE_DISTANCE, MILES_PER_HOUR);// Prints the duration and distance
        route = new Route (locationsArray, duration, distance);
        return route;
    }

    @Override
    public void notifyObservers() {
        orderPrinter.update(nextLocation);
        lmd.update(nextLocation);
    }

    @Override
    public void registerObservers(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObservers(Observer observer) {
        for(int i = 0; i < observers.size(); i++){
            if(observer == observers.get(i)) {
                observers.remove(i);
                break;
            }
        }
    }
}