import java.io.IOException;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        AddressGenerator generator = new AddressGenerator();
        try {
            generator.generateAddresses();
            //You can change the strategy used by the simulation by changing the strat parameter.
            //Example: SimulationDriver sim_left = new SimulationDriver("random_addresses.txt", "left");
            SimulationDriver sim = new SimulationDriver("random_addresses.txt", "left");
            //This parameter allows you to decide if you want to see the gui or not. Has an issue currently where the
            //Jframe is still created but doesn't show, however this does not impact operation.
            boolean gui = true;
            //The gui does not currently support two trucks, however if you would like to compare the performance of the
            //two trucks, you can run the two simulations together with gui set to false and it will work fine.
            Truck truck = new Truck();
            //LocationMapDisplay lmd = new LocationMapDisplay(102, 102, 600, 600, sim, truck.getLocation(), gui);
            Route route = sim.runSimulation();
            RouteDriver.runRoute(route, sim.locations);
            route.printLocations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
