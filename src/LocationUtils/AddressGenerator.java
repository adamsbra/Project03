package LocationUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class AddressGenerator {

    //Define filename
    private String filename = "random_addresses.txt";
    //Just for storing south and east.
    private String[] directions = {"South", "East"};
    private final int NUMBER_OF_BLOCKS = 10;
    private final int SANDWICHES_MAX = 3;
    private final int CONDIMENTS_MAX = 7;

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
        //Prints out the house number, street type, and street number.
        //Street number is random number 1 through 20
        String order = createOrderString();
        return house_number + " " + direction + " " + street_number + " " + time.toString() + " " + order + "\n";


    }

    public void generateAddresses(int amt) throws IOException {
        //Opens a new file
        File file = new File(filename);
        //Creates a buffered writer
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < amt; i++){
            //Writes a random address 100 times.
            writer.write(createRandomAddress());
        }
        //Close writer
        writer.close();
    }

    public String createOrderString(){
        String order = "";
        Random rand = new Random();
        for (int i = 0; i < rand.nextInt(SANDWICHES_MAX) + 1; i++) {
            int bread = rand.nextInt(2);
            if (bread == 0) {
                order += "roll";
            }
            if (bread == 1) {
                order += "wrap";
            }
            for (int j = 0; j < rand.nextInt(CONDIMENTS_MAX) + 1; j++) {
                int ingredient = rand.nextInt(7);
                switch (ingredient) {
                    case 0:
                        order += ",cheese";
                        break;
                    case 1:
                        order += ",ham";
                        break;
                    case 2:
                        order += ",lettuce";
                        break;
                    case 3:
                        order += ",mayonnaise";
                        break;
                    case 4:
                        order += ",mustard";
                        break;
                    case 5:
                        order += ",tomato";
                        break;
                    case 6:
                        order += ",turkey";
                        break;
                }
            }
            order += " ";
        }
        return order;
    }
}
