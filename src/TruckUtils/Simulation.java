package TruckUtils;

import LocationUtils.Location;
import LocationUtils.LocationsQueue;

import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public interface Simulation {

    //speed in mph
    int STOP_UNITS = 5;
    int MOVE_UNITS = 1;
    int LEFT_UNITS = 4;
    int RIGHT_UNITS = 2;

    ArrayList<Point> runSimulation(Truck truck, TruckTracker tracker) throws InterruptedException;

}

class LeftSimulation implements Simulation{

    private int stepDuration = 0;
    private int distance = 0;


    @Override
    public ArrayList<Point> runSimulation(Truck truck, TruckTracker tracker) {
        ArrayList<Point> movements = new ArrayList<>();
        LocationsQueue locationsQueueInstance = LocationsQueue.getLocationsQueueInstance();
        PriorityQueue<Location> locations = locationsQueueInstance.locationsQueue;
        while (locations.size() != 0){
            truck.setCurrentStepDuration(1);
            Location nextLocation = locations.peek();
            Location truckLocation = truck.getLocation();
            if(truck.atDistributionCenter() && truck.isWaiting(nextLocation)){
                truck.updateCurrentTime();
                truck.update();
                tracker.printDetails();
                continue;
            }
            truck.setAtLocation(false);
            if (truck.isWaiting(nextLocation)){
                nextLocation = Truck.DISTRIBUTION_CENTER;
                truck.setNextLocation(Truck.DISTRIBUTION_CENTER);
            }
            else {
                nextLocation = locations.poll();
                truck.setNextLocation(nextLocation);
            }
            while (truckLocation.east != nextLocation.east || truckLocation.south != nextLocation.south) {
                int currentDuration = 1;
                if (!truck.isMoving) {
                    if (truckLocation.direction.equalsIgnoreCase("east")) {
                        int east = nextLocation.east - truck.getLocation().east;
                        if (east > 0) {
                            truck.moveEast();
                        }
                        else if (east < 0) {
                            truck.moveWest();
                        }
                    } else if (truckLocation.direction.equalsIgnoreCase("south")) {
                        int south = nextLocation.south - truck.getLocation().south;
                        if (south > 0) {
                            truck.moveSouth();
                        }
                        else if (south < 0) {
                            truck.moveNorth();
                        }
                    }
                    truck.isMoving = true;
                    currentDuration = MOVE_UNITS;
                    truckLocation = truck.getLocation();
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
                    currentDuration = MOVE_UNITS;
                }
                else if (truck.atIntersection()){
                    int south = nextLocation.south - truck.getLocation().south;
                    int east = nextLocation.east - truck.getLocation().east;
                    if (truck.isMovingEast){
                        if (east > 0){
                            currentDuration = MOVE_UNITS;
                            truck.moveEast();
                        }
                        else {
                            currentDuration = LEFT_UNITS;
                            truck.moveNorth();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingSouth){
                        if (south > 0){
                            currentDuration = MOVE_UNITS;
                            truck.moveSouth();
                        }
                        else {
                            currentDuration = LEFT_UNITS;
                            truck.moveEast();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingNorth){
                        if (south < 0){
                            currentDuration = MOVE_UNITS;
                            truck.moveNorth();
                        }
                        else {
                            currentDuration = LEFT_UNITS;
                            truck.moveWest();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingWest){
                        if (east < 0){
                            currentDuration = MOVE_UNITS;
                            truck.moveWest();
                        }
                        else {
                            currentDuration = LEFT_UNITS;
                            truck.moveSouth();
                        }
                        truckLocation = truck.getLocation();
                    }
                }
                truck.setCurrentStepDuration(currentDuration);
                truck.updateCurrentTime();
                truck.update();
                tracker.printDetails();
                movements.add(new Point(truckLocation.east, truckLocation.south));
                truck.addDistance();
            }
            truck.setAtLocation(true);
            truck.setCurrentStepDuration(STOP_UNITS);
            truck.updateCurrentTime();
            truck.update();
            tracker.printDetails();
            movements.add(new Point(truckLocation.east, truckLocation.south));
        }
        return movements;
    }
}

class RightSimulation implements Simulation{

    private int distance = 0;


    @Override
    public ArrayList<Point> runSimulation(Truck truck, TruckTracker tracker){
        ArrayList<Point> movements = new ArrayList<>();
        LocationsQueue locationsQueueInstance = LocationsQueue.getLocationsQueueInstance();
        PriorityQueue<Location> locations = locationsQueueInstance.locationsQueue;
        while (locations.size() != 0) {
            truck.setCurrentStepDuration(1);
            Location nextLocation = locations.peek();
            Location truckLocation = truck.getLocation();
            if(truck.atDistributionCenter() && truck.isWaiting(nextLocation)){
                truck.updateCurrentTime();
                truck.update();
                tracker.printDetails();
                continue;
            }
            truck.setAtLocation(false);
            if (truck.isWaiting(nextLocation)){
                nextLocation = Truck.DISTRIBUTION_CENTER;
                truck.setNextLocation(Truck.DISTRIBUTION_CENTER);
            }
            else {
                nextLocation = locations.poll();
                truck.setNextLocation(nextLocation);
            }
            while (truckLocation.east != nextLocation.east || truckLocation.south != nextLocation.south) {
                int currentDuration = 1;
                if (!truck.isMoving) {
                    if (truckLocation.direction.equalsIgnoreCase("east")) {
                        int east = nextLocation.east - truck.getLocation().east;
                        if (east > 0) {
                            truck.moveEast();
                        } else {
                            truck.moveWest();
                        }
                    } else if (truckLocation.direction.equalsIgnoreCase("south")) {
                        int south = nextLocation.south - truck.getLocation().south;
                        if (south > 0) {
                            truck.moveSouth();
                        } else {
                            truck.moveNorth();
                        }
                    }
                    truck.isMoving = true;
                    truckLocation = truck.getLocation();
                    currentDuration = MOVE_UNITS;
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
                    currentDuration = MOVE_UNITS;

                } else if (truck.atIntersection()) {
                    int south = nextLocation.south - truck.getLocation().south;
                    int east = nextLocation.east - truck.getLocation().east;
                    if (truck.isMovingEast) {
                        if (east > 0) {
                            currentDuration = MOVE_UNITS;
                            truck.moveEast();
                        } else {
                            currentDuration = RIGHT_UNITS;
                            truck.moveSouth();
                        }
                        truckLocation = truck.getLocation();
                    } else if (truck.isMovingSouth) {
                        if (south > 0) {
                            currentDuration = MOVE_UNITS;
                            truck.moveSouth();
                        } else {
                            currentDuration = RIGHT_UNITS;
                            truck.moveWest();
                        }
                        truckLocation = truck.getLocation();
                    } else if (truck.isMovingNorth) {
                        if (south < 0) {
                            currentDuration = MOVE_UNITS;
                            truck.moveNorth();
                        } else {
                            currentDuration = RIGHT_UNITS;
                            truck.moveEast();
                        }
                        truckLocation = truck.getLocation();
                    } else if (truck.isMovingWest) {
                        if (east < 0) {
                            truck.moveWest();
                            currentDuration = MOVE_UNITS;
                        } else {
                            currentDuration = RIGHT_UNITS;
                            truck.moveNorth();
                        }
                        truckLocation = truck.getLocation();
                    }
                }
                distance++;
                truck.setCurrentStepDuration(currentDuration);
                truck.updateCurrentTime();
                truck.update();
                tracker.printDetails();
                movements.add(new Point(truckLocation.east, truckLocation.south));
                truck.addDistance();
            }
            truck.setAtLocation(true);
            truck.setCurrentStepDuration(STOP_UNITS);
            truck.updateCurrentTime();
            truck.update();
            tracker.printDetails();
            movements.add(new Point(truckLocation.east, truckLocation.south));
        }
        return movements;
    }
}
