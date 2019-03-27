import java.io.IOException;
import java.time.LocalTime;

public class main {

    public static void main(String[] args) throws InterruptedException {
        AddressGenerator generator = new AddressGenerator();
        try {
            generator.generateAddresses();
            Route route = new Route("random_addresses.txt", "right");
            Truck truck = new Truck();
            route.printLocations();
            LocationMapDisplay lmd = new LocationMapDisplay(102, 102, 600, 600, route, truck);
            route.runSimulation(lmd);
//            route.createRoute(lmd);
//            map.addTruckLocation(route.truck);
//            map.printLocations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
