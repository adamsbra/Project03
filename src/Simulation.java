import javafx.util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

public interface Simulation {

    //speed in mph
    int STOP_UNITS = 5;
    int MOVE_UNITS = 1;
    int LEFT_UNITS = 4;
    int RIGHT_UNITS = 4;

    public void runSimulation(Truck truck) throws InterruptedException;

}

class LeftSimulation implements Simulation{

    private int duration = 0;
    private int distance = 0;


    @Override
    public void runSimulation(Truck truck) {
        while (truck.locations.size() != 0){
            Location nextLocation = truck.locations.poll();
            truck.setNextLocation(nextLocation);
            Location truckLocation = truck.getLocation();
            while (truckLocation.east != nextLocation.east || truckLocation.south != nextLocation.south) {
                int currentDuration = 0;
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
                distance++;
                duration += currentDuration;
                truck.setCurrentDuration(currentDuration);
                truck.updateGui();
            }
            truck.setNextLocation(nextLocation);
            duration += STOP_UNITS;
            truck.setCurrentDuration(STOP_UNITS);
            truck.updateGui();
        }
    }
}

class RightSimulation implements Simulation{

    private int duration = 0;
    private int distance = 0;


    @Override
    public void runSimulation(Truck truck){
        while (truck.locations.size() != 0) {
            Location nextLocation = truck.locations.poll();
            truck.setNextLocation(nextLocation);
            Location truckLocation = truck.getLocation();
            while (truckLocation.east != nextLocation.east || truckLocation.south != nextLocation.south) {
                int currentDuration = 0;
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
                duration += currentDuration;
                truck.setCurrentDuration(currentDuration);
                truck.updateGui();
            }
            truck.setNextLocation(nextLocation);
            duration += STOP_UNITS;
            truck.setCurrentDuration(STOP_UNITS);
            truck.updateGui();
        }
    }
}
