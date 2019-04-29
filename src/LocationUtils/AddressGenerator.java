package LocationUtils;

import ItemUtils.Item;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import ItemUtils.ItemGenerator;
import ItemUtils.Order;

public class AddressGenerator {

    //Define filename
    private String filename = "random_addresses.txt";
    //Just for storing south and east.
    private String[] directions = {"South", "East"};
    private final int NUMBER_OF_BLOCKS = 10;
    private final int NUMBER_OF_ADDRESSES = 100;

    private LocalTime time = LocalTime.of(7, 0, 0);

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
            house_number = rand.nextInt(NUMBER_OF_BLOCKS * 100 + 1);
        }

        String direction = directions[rand.nextInt(2)];
        int street_number = rand.nextInt(10 - 1) + 1;
        ArrayList<Item> items = new ArrayList<>();
        items.add(ItemGenerator.sandwhich.get(rand.nextInt(3)));
        items.add(ItemGenerator.drinks.get(rand.nextInt(3)));
        items.add(ItemGenerator.chips.get(rand.nextInt(2)));
        Order order = new Order(items, time);
        String orderString = order.toString();
        //Prints out the house number, street type, and street number.
        //Street number is random number 1 through 20
        return house_number + " " + direction + " " + street_number + " " + time.toString() +  " " + orderString + "\n";


    }

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
