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


    public void printLocations(){
        for (Location location : locations) {
            System.out.println(location.toString() + " " + location.distance + " " + location.east + " " + location.south + " " + location.time);
        }
    }

//    public void createRoute(){
//        PriorityQueue<Location> locations_copy = locations;
//        ArrayList<Pair<Integer, Integer>> loc_map = new ArrayList<>();
//        while (locations_copy.size() != 0){
//            Location currentLocation = truck.getLocation();
//            Location nextLocation = locations_copy.poll();
//            while (currentLocation != nextLocation) {
//                if (!truck.isMoving) {
//                    if (truck.getLocation().direction.equalsIgnoreCase("east")) {
//                        int south = nextLocation.south - truck.getLocation().south;
//                        if (south < 0) {
//                            truck.moveEast();
//
//                        } else if (south > 0) {
//                            truck.moveWest();
//                        } else {
//                            return;
//                        }
//
//                    } else if (truck.getLocation().direction.equalsIgnoreCase("south")) {
//                        int east = nextLocation.east - truck.getLocation().east;
//                        if (east < 0) {
//                            truck.moveNorth();
//
//                        } else if (east > 0) {
//                            truck.moveSouth();
//                        } else {
//                            return;
//                        }
//                    }
//                    truck.isMoving = true;
//                    loc_map.add(new Pair(truck.getLocation().east, truck.getLocation().south));
//                }
//                while (!truck.atIntersection()){
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
//                    loc_map.add(new Pair(truck.getLocation().east, truck.getLocation().south));
//                }
//                int south = Math.abs(nextLocation.south - truck.getLocation().south);
//                int east = Math.abs(nextLocation.east - truck.getLocation().east);
//                if (east > south){
//                    truck.moveSouth();
//                }
//                else if (south < east){
//                    truck.moveEast();
//                }
//                else if (east == 0){
//                    truck.moveEast();
//                }
//                else if (south == 0){
//                    truck.moveSouth();
//                }
//                else if ()
//
//
//            }
//
//        }
//    }

}