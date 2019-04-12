import java.time.LocalTime;

public interface Observer {

    public void updateGui(Location truckLocation, Location nextLocation);
}
