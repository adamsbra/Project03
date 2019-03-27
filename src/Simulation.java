import java.time.LocalTime;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

public interface Simulation {

    double BLOCK_DISTANCE = 1320;
    double HOUSE_DISTANCE = BLOCK_DISTANCE / 9;
    public void runSimulation(LocationMapDisplay lmd, PriorityQueue<Location> locations, Truck truck) throws InterruptedException;

}

class LeftSimulation implements Simulation{

    Location nextLocation;
    Location truckLocation;
    LocationMapDisplay lmd;
    int duration = 0;
    int distance = 0;
    final int STEP_TIME = 200;
    final int STOP_UNITS = 5;
    final int MOVE_UNITS = 1;
    final int LEFT_UNITS = 4;

    @Override
    public void runSimulation(LocationMapDisplay loc_md, PriorityQueue<Location> locations, Truck truck) throws InterruptedException {
        this.lmd = loc_md;
        PriorityQueue<Location> locations_copy = locations;
        while (locations_copy.size() != 0){
            nextLocation = locations_copy.poll();
            lmd.nextLocation = nextLocation;
            lmd.truck = truck;
            Timer timer = new Timer();
            while (truck.getLocation().east != nextLocation.east || truck.getLocation().south != nextLocation.south) {
                int current_duration = 0;
                if (!truck.isMoving) {
                    if (truck.getLocation().direction.equalsIgnoreCase("east")) {
                        int east = nextLocation.east - truck.getLocation().east;
                        if (east > 0) {
                            truck.moveEast();
                        }
                        else if (east < 0) {
                            truck.moveWest();
                        }



                    } else if (truck.getLocation().direction.equalsIgnoreCase("south")) {
                        int south = nextLocation.south - truck.getLocation().south;
                        if (south > 0) {
                            truck.moveSouth();
                        }
                        else if (south < 0) {
                            truck.moveNorth();
                        }
                        else {
                            return;
                        }
                    }
                    truck.isMoving = true;
                    current_duration = MOVE_UNITS;
                    truckLocation = truck.getLocation();
                    Thread.sleep(current_duration * STEP_TIME);
                }
                else if (!truck.atIntersection()){
                    if (truck.isMovingEast){
                        truck.moveEast();
                    }
                    else if (truck.isMovingWest){
                        truck.moveWest();
                    }
                    else if (truck.isMovingNorth){
                        truck.moveNorth();
                    }
                    else if (truck.isMovingSouth){
                        truck.moveSouth();
                    }
                    truckLocation = truck.getLocation();
                    current_duration = MOVE_UNITS;
                }
                else if (truck.atIntersection()){
                    int south = nextLocation.south - truck.getLocation().south;
                    int east = nextLocation.east - truck.getLocation().east;
                    if (truck.isMovingEast){
                        if (east > 0){
                            current_duration = MOVE_UNITS;
                            truck.moveEast();
                        }
                        else {
                            current_duration = LEFT_UNITS;
                            truck.moveNorth();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingSouth){
                        if (south > 0){
                            current_duration = MOVE_UNITS;
                            truck.moveSouth();
                        }
                        else {
                            current_duration = LEFT_UNITS;
                            truck.moveEast();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingNorth){
                        if (south < 0){
                            current_duration = MOVE_UNITS;
                            truck.moveNorth();
                        }
                        else {
                            current_duration = LEFT_UNITS;
                            truck.moveWest();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingWest){
                        if (east < 0){
                            current_duration = MOVE_UNITS;
                            truck.moveWest();
                        }
                        else if (east >= 0){
                            current_duration = LEFT_UNITS;
                            truck.moveSouth();
                        }
                        else{
                            return;
                        }
                        truckLocation = truck.getLocation();
                    }
                }
                duration += current_duration;
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        lmd.repaintTask();
                    }
                }, current_duration * STEP_TIME);
                System.out.println(duration);
            }
            truckLocation = truck.getLocation();
            lmd.map[nextLocation.east][nextLocation.south] = "";
            duration += 5;
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    lmd.repaintTask();
                }
            },  STOP_UNITS * STEP_TIME);
        }
    }
}

class RightSimulation implements Simulation{

    Location nextLocation;
    Location truckLocation;
    int duration = 0;
    int distance = 0;
    LocationMapDisplay lmd;
    final int STEP_TIME = 200;
    final int STOP_UNITS = 5;
    final int MOVE_UNITS = 1;
    final int RIGHT_UNITS = 4;

    @Override
    public void runSimulation(LocationMapDisplay lmd, PriorityQueue<Location> locations, Truck truck) throws InterruptedException {
          PriorityQueue<Location> locations_copy = locations;
          while (locations_copy.size() != 0){
              nextLocation = locations_copy.poll();
            lmd.nextLocation = nextLocation;
            lmd.truck = truck;
            this.lmd = lmd;
            while (truck.getLocation().east != nextLocation.east || truck.getLocation().south != nextLocation.south) {
                int current_duration = 0;
                if (!truck.isMoving) {
                    if (truck.getLocation().direction.equalsIgnoreCase("east")) {
                        int east = nextLocation.east - truck.getLocation().east;
                        if (east > 0) {
                            truck.moveEast();
                        }
                        else if (east < 0) {
                            truck.moveWest();
                        }
                        else{
                            return;
                        }


                    } else if (truck.getLocation().direction.equalsIgnoreCase("south")) {
                        int south = nextLocation.south - truck.getLocation().south;
                        if (south > 0) {
                            truck.moveSouth();
                        } else if (south < 0) {
                            truck.moveNorth();
                        } else if (south == 0){
                            return;
                        }
                    }
                    truck.isMoving = true;
                    truckLocation = truck.getLocation();
                    current_duration = MOVE_UNITS;
                }
                else if (!truck.atIntersection()){
                    if (truck.isMovingEast){
                        truck.moveEast();
                    }
                    else if (truck.isMovingWest){
                        truck.moveWest();
                    }
                    else if (truck.isMovingNorth){
                        truck.moveNorth();
                    }
                    else if (truck.isMovingSouth){
                        truck.moveSouth();
                    }
                    truckLocation = truck.getLocation();
                    current_duration = MOVE_UNITS;

                }
                else if (truck.atIntersection()){
                    int south = nextLocation.south - truck.getLocation().south;
                    int east = nextLocation.east - truck.getLocation().east;
                    if (truck.isMovingEast){
                        if (east > 0){
                            current_duration = MOVE_UNITS;
                            truck.moveEast();
                        }
                        else {
                            current_duration = RIGHT_UNITS;
                            truck.moveSouth();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingSouth){
                        if (south > 0){
                            current_duration = MOVE_UNITS;
                            truck.moveSouth();
                        }
                        else {
                            current_duration = RIGHT_UNITS;
                            truck.moveWest();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingNorth){
                        if (south < 0){
                            current_duration = MOVE_UNITS;
                            truck.moveNorth();
                        }
                        else {
                            current_duration = RIGHT_UNITS;
                            truck.moveEast();
                        }
                        truckLocation = truck.getLocation();
                    }
                    else if (truck.isMovingWest){
                        if (east < 0){
                            truck.moveWest();
                            current_duration = MOVE_UNITS;
                        }
                        else {
                            current_duration = RIGHT_UNITS;
                            truck.moveNorth();
                        }
                        truckLocation = truck.getLocation();
                    }
                }
                distance++;
                duration += current_duration;
                Thread.sleep(STEP_TIME);
                lmd.repaint();

            }
            truckLocation = truck.getLocation();
            lmd.map[nextLocation.east][nextLocation.south] = "";
            duration += STOP_UNITS;
            System.out.println("Duration: " + duration / 60 + " Minutes");
            System.out.println("Distance: " + (double) Math.round(distance * HOUSE_DISTANCE / 5280) + " Miles");
            lmd.repaint();
          }
    }
}
