package LocationUtils;

import java.io.FileNotFoundException;
import java.util.PriorityQueue;

public class LocationsQueue {

    public PriorityQueue<Location> locationsQueue;
    private static LocationsQueue locationsQueueInstance = null;

    //Singleton implementation of the locations priority queue, not much else to say other than that.
    private LocationsQueue() {
        try {
            LocationInit init = new LocationInit("random_addresses.txt");
            locationsQueue = init.getLocations();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static LocationsQueue getLocationsQueueInstance(){

        if (locationsQueueInstance == null){
            locationsQueueInstance = new LocationsQueue();
        }
        return locationsQueueInstance;
    }

}
