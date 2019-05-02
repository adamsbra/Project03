package LocationUtils;

import TruckUtils.Truck;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.*;

public class LocationInit {

    public String filename;

    public LocationInit(String filename){
        this.filename = filename;
    }

    public PriorityQueue<Location> getLocations() throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(filename));
        PriorityQueue<Location> locations = new PriorityQueue<>();
        locations.add(Truck.DISTRIBUTION_CENTER);
        while(sc.hasNext()){
            String line[] = sc.nextLine().split(" ");
            int house_number = Integer.parseInt(line[0]);
            String direction = line[1];
            int street_number = Integer.parseInt(line[2]);
            LocalTime time = LocalTime.parse(line[3]);
            Location customerLocation = new Location(house_number, street_number, direction, time);
            locations.add(customerLocation);
        }
        return locations;
    }

//    public ArrayList<Point> runSimulation(TruckUtils.TruckUtils truck){
//        try {
//            return strategy.runSimulation(truck);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
