import java.time.LocalTime;
import java.util.PriorityQueue;

public interface Observer {

    public void updateGui(Location truckLocation, Location nextLocation, int currentDuration, PriorityQueue<Location> locations);
}
