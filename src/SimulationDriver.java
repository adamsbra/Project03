import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.Map;

public class SimulationDriver {

    public Simulation strategy;

    public SimulationDriver(String filename, String strat, Truck truck){
        try{
            addLocations(filename, truck);
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

    public void addLocations(String filename, Truck truck) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(filename));
        PriorityQueue<Location> locations = new PriorityQueue<>();
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
        truck.locations = locations;
    }


    public void printLocations(Truck truck) {
        for (Location location : truck.locations) {
            System.out.println(location.toString() + " " + location.distance + " " + location.east + " " + location.south + " " + location.time);
        }
    }

    public void runSimulation(Truck truck){
        try {
            strategy.runSimulation(truck);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
