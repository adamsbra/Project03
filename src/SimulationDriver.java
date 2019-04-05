import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.Map;

public class SimulationDriver {

    public PriorityQueue<Location> locations = new PriorityQueue<>();
    public Truck truck = new Truck();
    public Simulation strategy;
    public Route route;
    public boolean gui;

    public SimulationDriver(String filename, String strat){
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
            String order = line[4];
            Location customerLocation = new Location(house_number, street_number, direction, truck.getLocation(), time, order);
            locations.add(customerLocation);

        }
    }


    public void printLocations() {
        for (Location location : locations) {
            System.out.println(location.toString() + " " + location.distance + " " + location.east + " " + location.south + " " + location.time);
        }
    }

    public void runSimulation(LocationMapDisplay lmd, boolean gui){
        try {
            route = strategy.runSimulation(lmd, locations, truck, gui);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
