package LocationUtils;

import DecoratorStrategy.Order;
import DecoratorStrategy.OrderMaker;
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

    //Initializes the locations from a file, and adds them to a priority queue.
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
            ArrayList<String> orderlst = new ArrayList<>();
            for (int i = 4; i < line.length; i++){
                orderlst.add(line[i]);
            }
            Order order = OrderMaker.buildOrder(orderlst);

            Location customerLocation = new Location(house_number, street_number, direction, time, order);
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
