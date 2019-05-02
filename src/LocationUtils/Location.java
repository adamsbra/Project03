package LocationUtils;

import java.time.LocalTime;

public class Location implements Comparable<Location>{

//    private final double BLOCK_DISTANCE = 1320;
//    private final double HOUSE_DISTANCE = BLOCK_DISTANCE / 9; // New block distance, quarter of a mile converted to feet, divided by 9
//    private final double FEET_IN_MILE = 5280;

    public static final double HOUSE_DISTANCE = .03;

    public int street_number;
    public int house_number;
    public String direction;
    public double distance;
    public int east;
    public int south;
    public LocalTime time;

    //I added a new attribute distance which gets rid of the need for the treemap. Block distance is now measured in feet,
    //and adjustments have been made for it. Get Distance uses the truck as the origin location now.

    public Location(int house_number, int street_number, String direction, LocalTime time){
        this.house_number = house_number;
        this.street_number = street_number;
        this.direction = direction;
        this.time = time;

        //1 = 010;  11 = 110;  22 = 220... etc
        //0, 10, 20, is to specify that is not in that street, but you go up to that street number

        //910 South 9th
        //90 91
        //first number is east, second is south

        //I agree with this numbering, but how do you differentiate houses on two different sides of the street? If you
        //go to 9th street, and to the 910 house, there's a house on both sides of that location
        if(direction.equalsIgnoreCase("East")){
            //Subracting 10 because otherwise it puts the house in the wrong location.
            this.south = (street_number * 10) - 10;
            this.east = (this.house_number / 10);
        }
        else {
            //Subracting 10 because otherwise it puts the house in the wrong location.
            this.south = (this.house_number / 10);
            this.east = (street_number * 10) - 10;
        }
    }

    //Distanceless constructor for creating the truck location.
    public Location(int house_number, int street_number, String direction) {
        this.time = null;
        this.house_number = house_number;
        this.street_number = street_number;
        this.direction = direction;
        this.distance = 0;
        if (direction.equalsIgnoreCase("East")) {
            this.south = (street_number * 10) - 10;
            this.east = house_number / 10;
        } else {
            this.south = (this.house_number / 10);
            this.east = (street_number * 10) - 10;
        }
    }

    //getDistance now modifies temp values instead of modifying south and east directly.
    public double getDistance(Location origin){
        if(origin.south == south && origin.east == east){
            return 0;
        }
        //As far as I can tell, this acheives the same thing as the previous getDistance code.
        int southHouses = Math.abs(origin.south - south);
        int eastHouses = Math.abs(origin.east - east);

        //if we don't need the number
        //maybe use String getDistance, that way you can say 0.25 miles, and not just 0.25.
        return ((eastHouses + southHouses) * HOUSE_DISTANCE);
    }

    public void changeLocation(int east, int south){
        this.east = east;
        this.south = south;
    }

    public String toString(){
        return house_number + " " +  direction + " " +  street_number + "St";
    }

    //Comparator needed for adding the locations to the PriorityQueue.
    @Override
    public int compareTo(Location location) {
        return this.time.compareTo(location.time);
    }
}