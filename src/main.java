import java.io.IOException;

public class main {

    public static void main(String[] args){
        AddressGenerator generator = new AddressGenerator();
        try {
            generator.generateAddresses();
            Route route = new Route();
            Truck truck = new Truck();
            route.addLocations("random_addresses.txt");
            route.printLocations();
            LocationMapDisplay lmd = new LocationMapDisplay(201, 201, 804, 804, route, truck.getLocation());
//            map.addTruckLocation(route.truck);
//            map.printLocations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
