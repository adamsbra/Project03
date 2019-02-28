import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map;

public class Route {

    public PriorityQueue<Location> locations = new PriorityQueue<>();


    public void addRoutes(String filename) throws FileNotFoundException {
        Location truckLocation = new Location(910, 9, "South");
//        Location location = new Location(10, 1, "South", truckLocation);
//        Location location_2 = new Location(10, 2, "South", truckLocation);
//        locations.add(location);
//        locations.add(location_2);
        Scanner sc = new Scanner(new FileInputStream(filename));
        while(sc.hasNext()){
            String line[] = sc.nextLine().split(" ");
            int house_number = Integer.parseInt(line[0]);
            String direction = line[1];
            int street_number = Integer.parseInt(line[2]);
            Location customerLocation = new Location(house_number, street_number, direction, truckLocation);
            locations.add(customerLocation);
        }
    }

    public void printAddresses(){
//        for (Location location : locations) {
//            System.out.println(location.toString() + " " + location.distance + " " + location.east + " " + location.south);
//        }
    }

}