import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.Map;

public class Route {

    public PriorityQueue<Location> locations = new PriorityQueue<>();
    public Truck truck = new Truck();
    public ArrayList<Location> loc_array = new ArrayList<>();
    public Simulation strategy;

    public Route(String filename, String strat){
        try{
            addLocations(filename);
            if (strat.equalsIgnoreCase("left")){
                strategy = new LeftSimulation();
            }
            else if (strat.equalsIgnoreCase("right")){
                strategy = new RightSimulation();
            }
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }


    }

    public void addLocations(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(filename));
        while(sc.hasNext()){
            String line[] = sc.nextLine().split(" ");
            int house_number = Integer.parseInt(line[0]);
            String direction = line[1];
            int street_number = Integer.parseInt(line[2]);
            LocalTime time = LocalTime.parse(line[3]);
            Location customerLocation = new Location(house_number, street_number, direction, truck.getLocation(), time);
            locations.add(customerLocation);

        }
    }


    public void printLocations() {
        for (Location location : locations) {
            System.out.println(location.toString() + " " + location.distance + " " + location.east + " " + location.south + " " + location.time);
        }
    }

    public void runSimulation(LocationMapDisplay lmd){
        try {
            strategy.runSimulation(lmd, locations, truck);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public void createRoute(LocationMapDisplay lmd) throws InterruptedException{
//        PriorityQueue<Location> locations_copy = locations;
//        while (locations_copy.size() != 0){
//            Location nextLocation = locations_copy.poll();
//            while (truck.getLocation().east != nextLocation.east || truck.getLocation().south != nextLocation.south) {
//                if (!truck.isMoving) {
//                    if (truck.getLocation().direction.equalsIgnoreCase("east")) {
//                        int east = nextLocation.east - truck.getLocation().east;
//                        if (east > 0) {
//                            truck.moveEast();
//                        }
//                        else if (east < 0) {
//                            truck.moveWest();
//                        }
//                        else if (east == 0){
//                            return;
//                        }
//
//
//                    } else if (truck.getLocation().direction.equalsIgnoreCase("south")) {
//                        int south = nextLocation.south - truck.getLocation().south;
//                        if (south > 0) {
//                            truck.moveSouth();
//                        } else if (south < 0) {
//                            truck.moveNorth();
//                        } else if (south == 0){
//                            return;
//                        }
//                    }
//                    truck.isMoving = true;
//                    loc_array.add(truck.getLocation());
//                    lmd.repaintRoute();
//                }
//                else if (!truck.atIntersection()){
//                    if (truck.isMovingEast){
//                        truck.moveEast();
//                    }
//                    else if (truck.isMovingWest){
//                        truck.moveWest();
//                    }
//                    else if (truck.isMovingNorth){
//                        truck.moveNorth();
//                    }
//                    else if (truck.isMovingSouth){
//                        truck.moveSouth();
//                    }
//                    loc_array.add(truck.getLocation());
//                    lmd.repaintRoute();
//                }
//                else if (truck.atIntersection()){
//                    int south = nextLocation.south - truck.getLocation().south;
//                    int east = nextLocation.east - truck.getLocation().east;
//                    if (truck.isMovingEast){
//                        if (east > 0){
//                            truck.moveEast();
//                        }
//                        else if (east <= 0){
//                            truck.moveNorth();
//                        }
//                        else{
//                            return;
//                        }
//                        loc_array.add(truck.getLocation());
//                        lmd.repaintRoute();
//                    }
//                    if (truck.isMovingSouth){
//                        if (south > 0){
//                            truck.moveSouth();
//                        }
//                        else if (south <= 0){
//                            truck.moveEast();
//                        }
//                        else{
//                            return;
//                        }
//                        loc_array.add(truck.getLocation());
//                        lmd.repaintRoute();
//                    }
//                    if (truck.isMovingNorth){
//                        if (south < 0){
//                            truck.moveNorth();
//                        }
//                        else if (south >= 0){
//                            truck.moveWest();
//                        }
//                        else{
//                            return;
//                        }
//                        loc_array.add(truck.getLocation());
//                        lmd.repaintRoute();
//                    }
//                    if (truck.isMovingWest){
//                        if (east < 0){
//                            truck.moveWest();
//                        }
//                        else if (east >= 0){
//                            truck.moveSouth();
//                        }
//                        else{
//                            return;
//                        }
//                        loc_array.add(truck.getLocation());
//                        lmd.repaintRoute();
//                    }
//                }
//                Thread.sleep(1000);
//            }
//            loc_array.add(truck.getLocation());
//            lmd.repaintRoute();
//        }
    }
