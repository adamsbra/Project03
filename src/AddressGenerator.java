import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Random;

public class AddressGenerator {

    //Define filename
    private String filename = "random_addresses.txt";
    //Just for storing south and east.
    private String[] directions = {"South", "East"};
    private int current_hour = 10;
    private int current_minute = 0;

    private String createRandomAddress(){
        //Get a Random instance
        Random rand = new Random();
        //Instantiate house number
        int house_number = 0;
        int new_hour = 0;
        String time_of_day = "AM";
        int rand_minutes = rand.nextInt(10);
        if (current_minute + rand_minutes >= 60){
            current_hour += 1;
            current_minute = current_minute + rand_minutes - 60;
        }
        else{
            current_minute = current_minute + rand_minutes;
        }
        if (current_hour > 12){
            new_hour = current_hour - 12;
            time_of_day = "PM";
        }
        else if (current_hour == 12){
            new_hour = current_hour;
            time_of_day = "PM";
        }
        else{
            new_hour = current_hour;
        }
        //Weird comparison but it works, wont accept numbers with 00 in tens place
        while (house_number % 100 == 0|| house_number % 10 != 0){
            //Random numbers between 1 and 2000
            house_number = rand.nextInt(2000);
        }

        String direction = directions[rand.nextInt(2)];
        int street_number = rand.nextInt(20 - 1) + 1;

        //Prints out the house number, street type, and street number.
        //Street number is random number 1 through 20
        if (current_minute < 10){
            return house_number + " " + direction + " " + street_number + " " + new_hour + ":0" + current_minute + time_of_day +  "\n";
        }
        return house_number + " " + direction + " " + street_number + " " + new_hour + ":" + current_minute  + time_of_day + "\n";

    }

    public void generateAddresses() throws IOException {
        //Opens a new file
        File file = new File(filename);
        //Creates a buffered writer
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < 100; i++){
            //Writes a random address 100 times.
            writer.write(createRandomAddress());
        }
        //Close writer
        writer.close();
    }
}
