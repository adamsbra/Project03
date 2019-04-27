/*
Author : Team Null
Truck
 */

public class Truck{

    public final Location DISTRIBUTION_CENTER = new Location(510, 5, "East");

    private Location currentLocation = DISTRIBUTION_CENTER;
    public boolean isMovingEast = false;
    public boolean isMovingSouth = false;
    public boolean isMovingWest = false;
    public boolean isMovingNorth = false;
    public boolean isMoving = false;

    //returns current location of the truck
    public Location getLocation() {
        return currentLocation;
    }

    //these are really confusing, it increments south for move east because technically incrementing south houses means
    //you're moving east

    //truck is moving east
    public void moveEast(){
        currentLocation.east++;
        resetBooleans();
        isMovingEast = true;
    }

    //truck is moving west
    public void moveWest(){
        currentLocation.east--;
        resetBooleans();
        isMovingWest = true;
    }

    //truck is moving north
    public void moveNorth(){
        currentLocation.south--;
        resetBooleans();
        isMovingNorth = true;
    }
    //truck is moving south
    public void moveSouth(){
        currentLocation.south++;
        resetBooleans();
        isMovingSouth = true;
    }

    //resets direction the truck is moving every time the truck changes moving direction.
    public void resetBooleans(){
        isMovingEast = false;
        isMovingSouth = false;
        isMovingWest = false;
        isMovingNorth = false;
    }

    //Truck is at an intersection
    public boolean atIntersection(){
        return currentLocation.east % 10 == 0 && currentLocation.south % 10 == 0;
    }
}