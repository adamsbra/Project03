/*
Author : Team Null
Main
 */

import java.io.IOException;
import java.util.Scanner;

public class main {

    private static boolean gui = true;
    private static String strategy;


    public static void main(String[] args) {
        AddressGenerator generator = new AddressGenerator();
        try {
            generator.generateAddresses();
            UserInterface();

            //You can change the strategy used by the simulation by changing the strat parameter.
            //Example: SimulationDriver sim_left = new SimulationDriver("random_addresses.txt", "left");
            SimulationDriver sim = new SimulationDriver("random_addresses.txt", strategy);
            //This parameter allows you to decide if you want to see the gui or not. Has an issue currently where the
            //Jframe is still created but doesn't show, however this does not impact operation.

            //The gui does not currently support two trucks, however if you would like to compare the performance of the
            //two trucks, you can run the two simulations together with gui set to false and it will work fine.
            Truck truck = new Truck();
            LocationMapDisplay lmd = new LocationMapDisplay(102, 102, 600, 600, sim, truck, gui);
            sim.runSimulation(lmd, gui);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//   creates small User Interface that allows the user decide the routing method (left or right) and if they want to display the map.
    private static void UserInterface(){

        Scanner scnr = new Scanner(System.in);
        System.out.println("Welcome to the Food Truck Delivery Simulation!");
        System.out.println();

        //Displaying the map
        while (true) {
            System.out.println("Do you want to display the map? yes(y) or no(n)?");
            String display = scnr.next();
            display = display.toLowerCase();

            if (display.equals("n")) {
                gui = false;
                break;
            }
            else if (display.equals("y")) {
                gui = true;
                break;
            }
            else {
                System.out.println("Wrong input. Try again...");
            }

        }
        //Routing method
        while(true){
            System.out.println("What routing method would you like to use? Left(l) or right(r)?");
            strategy = scnr.next();
            strategy = strategy.toLowerCase();

            if (strategy.equals("l")) {
                strategy = "left";
                break;
            }
            else if (strategy.equals("r")) {
                strategy = "right";
                break;
            }
            else{
                System.out.println("Wrong input. Try again...");
            }
        }

    }
}
