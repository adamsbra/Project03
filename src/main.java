
import LocationUtils.AddressGenerator;
import LocationUtils.LocationInit;
import LocationUtils.LocationsQueue;
import TruckUtils.Truck;
import TruckUtils.TruckTracker;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        AddressGenerator generator = new AddressGenerator();
        try {
            generator.generateAddresses();
            Scanner scnr = new Scanner(System.in);
            System.out.println("What strategy would you like to use?");
            String strategy = scnr.nextLine();
            System.out.println("Would you like a gui?");
            String gui_text = scnr.nextLine();
            boolean gui = gui_text.equalsIgnoreCase("yes");
            Truck truck = new Truck(strategy);
            TruckTracker tracker = new TruckTracker();
            truck.registerObserver(tracker);

            if (gui) {
                // + 20 for the mac toolbar, sets absolute resolution
                LocationMapDisplay lmd = new LocationMapDisplay(10, 606, 606 + 20, truck);
                truck.registerObserver(lmd);
            }
            //Movements not currently used, could be in the future to save routes.
            ArrayList<Point> movements = truck.runStrategy(tracker);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
