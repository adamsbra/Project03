import javafx.util.Pair;

import java.util.ArrayList;

public class Route {

    ArrayList<Pair<Integer, Integer>> locations;
    int duration;
    int distance;

    Route(ArrayList<Pair<Integer, Integer>> locations, int duration, int distance){
        this.locations = locations;
        this.duration = duration;
        this.distance = distance;
    }
}
