import javafx.util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

public interface Simulation {

    //.03 miles apart
    double BLOCK_DISTANCE = 1320;
    double HOUSE_DISTANCE = BLOCK_DISTANCE / 9;
    //speed in mph
    int STEP_TIME = 200;
    int STOP_UNITS = 5;
    int MOVE_UNITS = 1;
    int LEFT_UNITS = 4;
    int RIGHT_UNITS = 4;

    public Route runSimulation(PriorityQueue<Location> locations, Truck truck) throws InterruptedException;

}

class LeftSimulation implements Simulation{

    Location nextLocation;
    Location truckLocation;
    Route route;
    ArrayList<Pair<Integer, Integer>> locationsArray;
    ArrayList<Pair<Integer, Integer>> nextLocations;
    ArrayList<Integer> turnTimes;
    int duration = 0;
    int distance = 0;


    @Override
    public Route runSimulation(PriorityQueue<Location> locations, Truck truck) throws InterruptedException {
        this.locationsArray = new ArrayList<>();
        this.turnTimes = new ArrayList<>();
        this.nextLocations = new ArrayList<>();
        PriorityQueue<Location> locations_copy = locations;
        while (locations_copy.size() != 0){
            nextLocation = locations_copy.poll();
            truck.setNextLocation(nextLocation);
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
                locationsArray.add(new Pair(truckLocation.east, truckLocation.south));
                nextLocations.add(new Pair(nextLocation.east, nextLocation.south));
                turnTimes.add(current_duration);
            }
            truckLocation = truck.getLocation();
            duration += STOP_UNITS;
            DecimalFormat f = new DecimalFormat("##.00");
            locationsArray.add(new Pair(truckLocation.east, truckLocation.south));
            turnTimes.add(STOP_UNITS);
        }
        route = new Route(locationsArray, nextLocations, locations, turnTimes, duration, distance);
        return route;
    }
}

class RightSimulation implements Simulation{

    Location nextLocation;
    Location truckLocation;
    int duration = 0;
    int distance = 0;
    Route route;
    LocationMapDisplay lmd;
    ArrayList<Pair<Integer, Integer>> locationsArray;


    @Override
    public Route runSimulation(PriorityQueue<Location> locations, Truck truck) throws InterruptedException {
        PriorityQueue<Location> locations_copy = locations;
        locationsArray = new ArrayList<>();
        while (locations_copy.size() != 0) {
            nextLocation = locations_copy.poll();
            truck.setNextLocation(nextLocation);
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
                truck.updateGui();
            }
            truckLocation = truck.getLocation();
            duration += STOP_UNITS;
            locationsArray.add(new Pair(truckLocation.east, truckLocation.south));
            truck.updateGui();
        }
        DecimalFormat f = new DecimalFormat("##.00");
        System.out.println("Duration: " + duration / 60 + " Minutes, " + duration % 60 + " Seconds");
        System.out.println("Distance: " + f.format(distance * HOUSE_DISTANCE / 5280) + " Miles");

        return route;
    }
}
