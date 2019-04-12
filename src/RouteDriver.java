import javafx.util.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class RouteDriver {

    public static void runRoute(Route route, PriorityQueue<Location> houses){
        ArrayList<Pair<Integer, Integer>> locations = route.locations;
        ArrayList<Integer> times = route.turnTimes;
        LocationMapDisplay lmd = new LocationMapDisplay(102, 102, 600, 600, route);
        lmd.printLocations();

    }
}
