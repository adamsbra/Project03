import java.text.DecimalFormat;
import java.time.LocalTime;

public class OrderPrinter implements Observer {
//Location, time, order filled
    double time = 0.0;

    public OrderPrinter(int duration, int distanceInFeet, double HOUSE_DISTANCE, int MILES_PER_HOUR){
        double distanceInMiles =  distanceInFeet * HOUSE_DISTANCE / 5280;
        time = ((distanceInMiles / MILES_PER_HOUR) * 60) * 60;// time in seconds
        int minute = (int) (time / 60);
        int seconds = (int) (time % 60);

        DecimalFormat f = new DecimalFormat("##.00");
//        System.out.println("Duration: " + minute + " Minutes, " + seconds + " Seconds");
        System.out.println("Distance: " + f.format(distanceInMiles) + " Miles");

    }


    @Override
    public void update(Location truckLocation) {
//        LocalTime newTime = truckLocation.time.plusSeconds((long) time);//add duration to order time
        LocalTime newTime = LocalTime.of(7,2,1).plusSeconds((long) time);//add duration to 7am
        System.out.println("Location: " + truckLocation + ", Time: " + newTime.toString() + ", Order: " + truckLocation.getOrder());
        System.out.println();
    }
}
