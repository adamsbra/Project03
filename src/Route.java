import javafx.util.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Route {

    public PriorityQueue<Location> locations;
    public Truck truck;

    public Route(Truck truck, PriorityQueue<Location> locations){
        this.truck = truck;
        this.locations = locations;
    }
}
