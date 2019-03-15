import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map;

public class Route {

    public PriorityQueue<Location> locations = new PriorityQueue<>();
    public Location truck = new Location(910, 9, "South");


    public void addRoutes(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(filename));
        while(sc.hasNext()){
            String line[] = sc.nextLine().split(" ");
            int house_number = Integer.parseInt(line[0]);
            String direction = line[1];
            int street_number = Integer.parseInt(line[2]);
            Location customerLocation = new Location(house_number, street_number, direction, truck);
            locations.add(customerLocation);
        }
    }

    public void printLocations(){
//        for (Location location : locations) {
//            System.out.println(location.toString() + " " + location.distance + " " + location.east + " " + location.south);
//        }
    }

}