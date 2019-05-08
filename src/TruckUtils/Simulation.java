package TruckUtils;

import LocationUtils.Location;
import LocationUtils.LocationsQueue;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

import static java.time.temporal.ChronoUnit.MINUTES;

//Strategy pattern that implements the Simulation. Simulation controls the truck and it's properties, as well as the update
//for the tracker.
public interface Simulation {

    //speed in mph
    int STOP_UNITS = 5;
    int MOVE_UNITS = 1;
    int LEFT_UNITS = 4;
    int RIGHT_UNITS = 2;

    ArrayList<Point> runSimulation(Truck truck, TruckTracker tracker) throws InterruptedException;

}

//Simulation when only left turns are taken.
class LeftSimulation implements Simulation{

    //Most documentation will be done for simulation here, because right is extremely similar with the exception of some movements.
    @Override
    public ArrayList<Point> runSimulation(Truck truck, TruckTracker tracker) {
        //Initialize an arraylist to store the movements of the truck. Not currently used.
        ArrayList<Point> movements = new ArrayList<>();
        //Grabs singleton PriorityQueue with all locations
        LocationsQueue locationsQueueInstance = LocationsQueue.getLocationsQueueInstance();
        PriorityQueue<Location> locations = locationsQueueInstance.locationsQueue;
        //Loops until the queue is empty
        while (locations.size() != 0){
            //Reset the current duration of the truck. Used to calculate things such as time and painting delay.
            truck.setCurrentStepDuration(1);
            //Check the next location to see if we are waiting to deliver.
            Location nextLocation = locations.peek();
            Location truckLocation = truck.getLocation();
            if(truck.isWaiting(nextLocation) && locations.size() > 1){
                truck.updateCurrentTime();
                truck.update();
                tracker.printDetails();
                continue;
            }
            else {
                truck.setAtLocation(false);
                //Poll to remove the location from the queue.
                nextLocation = locations.poll();
                truck.setNextLocation(nextLocation);
            }
            //Loop until the locations match.
            while (truckLocation.east != nextLocation.east || truckLocation.south != nextLocation.south) {
                int currentDuration = 1;
                //Movements are seperated into three sections, when the truck is stopped, when the truck is moving down a street
                //and when the truck is at an intersection.
                if (!truck.isMoving) {
                    //If the truck is on an east road, we know we need to move horizontally. Next, determine if we
                    //should move east or west.
                    if (truckLocation.direction.equalsIgnoreCase("east")) {
                        int east = nextLocation.east - truck.getLocation().east;
                        if (east > 0) {
                            truck.moveEast();
                        }
                        else if (east < 0) {
                            truck.moveWest();
                        }
                    }
                    //Same process, just for vertical movement.
                    else if (truckLocation.direction.equalsIgnoreCase("south")) {
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
                //If we are not at an intersection, simply move in the direction we were moving previously.
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
                //If we are at an intersection, we first determine the previous direction we were going. Then, depending
                //on if we passed the location or we still need to go straight, turn left relative to the direction we were traveling.
                //For instance, if we are heading east, and the intersection is before the location, we are going to continue straight.
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
                //Set the step duration for painting delay, update the time of the truck based on that, and add the movement
                //to the list. As well, update tracker and distance.
                truck.setCurrentStepDuration(currentDuration);
                truck.updateCurrentTime();
                truck.update();
                tracker.printDetails();
                movements.add(new Point(truckLocation.east, truckLocation.south));
                truck.addDistance();
            }
            //If we reach the location, we set atLocation to true, and current duration is set to stop units.
            truck.setAtLocation(true);
            truck.setCurrentStepDuration(STOP_UNITS);
            truck.updateCurrentTime();
            truck.update();
            tracker.printDetails();
            if(nextLocation.order != null)
                System.out.println(nextLocation.order.toString());
            movements.add(new Point(truckLocation.east, truckLocation.south));
        }
        System.out.println("Total Distance - " + truck.distance);
        long minutes = LocalTime.of(7, 0, 0).until(truck.getCurrentTime(), MINUTES);
        System.out.println("Total Time - " + minutes + " minutes");
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
            if(truck.isWaiting(nextLocation) && locations.size() > 1){
                truck.updateCurrentTime();
                truck.update();
                tracker.printDetails();
                continue;
            }
            else {
                truck.setAtLocation(false);
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
            if(nextLocation.order != null)
                System.out.println(nextLocation.order.toString());
            movements.add(new Point(truckLocation.east, truckLocation.south));
        }
        System.out.println("Total Distance - " + truck.distance);
        long minutes = LocalTime.of(7, 0, 0).until(truck.getCurrentTime(), MINUTES);
        System.out.println("Total Time - " + minutes + " minutes");
        return movements;
    }
}
