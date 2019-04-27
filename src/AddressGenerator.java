/*
Author : Team Null
Generates random orders.
 */


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Random;

public class AddressGenerator {

    private final int NUMBER_OF_ADDRESSES = 3;

    //Define filename
    private String filename = "random_addresses.txt";
    //Just for storing south and east.
    private String[] directions = {"South", "East"};
    private String[] menu_sandwhich = {"Sandwhich_1", "Sandwhich_2", "Sandwhich_3"};
    private String[] menu_chips = {"Chips_1", "Chips_2"};
    private String[] menu_drink = {"Drink_1", "Drink_2", "Drink_3"};

    private LocalTime time = LocalTime.of(7, 0, 0);

    //create random addresses
    private String createRandomAddress(){
        //Get a Random instance
        Random rand = new Random();
        //Instantiate house number
        int house_number = 0;
        int rand_minutes = rand.nextInt(10);
        time = time.plusMinutes(rand_minutes);
        if (time.getHour() >= 18) {
            time = LocalTime.of(18, 0, 0);
        }
        //Weird comparison but it works, wont accept numbers with 00 in tens place
        while (house_number % 100 == 0|| house_number % 10 != 0){
            //Random numbers between 1 and 2000
            house_number = rand.nextInt(1000);
        }

        String direction = directions[rand.nextInt(2)];
        int street_number = rand.nextInt(10 - 1) + 1;
        String order = menu_sandwhich[rand.nextInt(3)] + " " + menu_chips[rand.nextInt(2)] + " " +
                menu_drink[rand.nextInt(3)];


        //Prints out the house number, street type, and street number.
        //Street number is random number 1 through 20
        return house_number + " " + direction + " " + street_number + " " + time.toString() +  " " + order + "\n";


    }

    //generates addresses
    public void generateAddresses() throws IOException {
        //Opens a new file
        File file = new File(filename);
        //Creates a buffered writer
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < NUMBER_OF_ADDRESSES; i++){
            //Writes a random address 100 times.
            writer.write(createRandomAddress());
        }
        //Close writer
        writer.close();
    }
}
