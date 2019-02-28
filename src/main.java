import java.io.IOException;

public class main {

    public static void main(String[] args){
        AddressGenerator generator = new AddressGenerator();
        try {
            generator.generateAddresses();
            Route route = new Route();
            route.addRoutes("random_addresses.txt");
            LocationMapDisplay map = new LocationMapDisplay(201, 201, route);
            map.printLocations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
