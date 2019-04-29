package TruckUtils;

import LocationUtils.Location;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;

public class TruckTracker implements Observer {

    public Location currentLocation;
    public LocalTime time;
    public boolean orderFulfilled;
    public double distance;
    public ArrayList<TrackerObserver> observers = new ArrayList<>();
    public DecimalFormat df = new DecimalFormat("###.##");


    @Override
    public void update(Truck truck) {
        //In order to get current location to work, you're gonna have to find a way to get the location to set itself with the
        //textual version, as of right now it only updates east and south instead of it's actual address. I have no
        //fucking clue how to do this, so good luck future you.
        this.currentLocation = truck.getLocation();
        this.time = truck.getCurrentTime();
        this.orderFulfilled = truck.atLocation;
        this.distance = truck.distance;
    }

    public void printDetails(){
        System.out.println("Current Location - " + currentLocation.toString() + ": Current Time - " + time + ": Order Completed - " + orderFulfilled + ": Distance Traveled - " + df.format(distance));
    }

    public String getDetails(){
        return "Current Location - " + currentLocation.toString() + ":Current Time - " + time + ": Order Completed - " + orderFulfilled + ": Distance Traveled - " + df.format(distance);
    }
}
