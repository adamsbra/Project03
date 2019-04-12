import javafx.util.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Route {

    public ArrayList<Pair<Integer, Integer>> locations;
    public PriorityQueue<Location> houseLocations;
    public ArrayList<Integer> turnTimes;
    public ArrayList<Pair<Integer, Integer>> nextLocations;
    public int duration;
    public int distance;

    Route(ArrayList<Pair<Integer, Integer>> locations, ArrayList<Pair<Integer, Integer>> nextLocations, PriorityQueue<Location> houseLocations, ArrayList<Integer> turnTimes, int duration, int distance){
        this.locations = locations;
        this.turnTimes = turnTimes;
        this.nextLocations = nextLocations;
        this.houseLocations = houseLocations;
        this.duration = duration;
        this.distance = distance;
    }

    public void printLocations(){
        for (int i = 0; i < locations.size(); i++){
            System.out.println(locations.get(i).getKey() + ": " + locations.get(i).getValue() + ": " + turnTimes.get(i));
        }
    }
}
