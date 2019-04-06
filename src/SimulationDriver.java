/*
Author : Team Null
Sets the simulation
 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SimulationDriver {

    public PriorityQueue<Location> locations = new PriorityQueue<>();
    public Truck truck = new Truck();
    public Simulation strategy;
    public Route route;

    //Constructor that allows the user to change the strategy(left or right)
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

    //Reads the file of orders and adds it to a priority queue
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

    //Prints locations in string format
    public void printLocations() {
        for (Location location : locations) {
            System.out.println(location.toString() + " " + location.distance + " " + location.east + " " + location.south + " " + location.time);
        }
    }

    //Runs GUI
    public void runSimulation(LocationMapDisplay lmd, boolean gui){
        try {
            route = strategy.runSimulation(lmd, locations, truck, gui);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
