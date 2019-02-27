import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class AddressGenerator {

    private String filename = "random_addresses.txt";
    //Wasn't sure how the naming conventions of the streets were taken care of, so I just used numbers and letters.
    private String[] directions = {"South", "East"};

    public String createRandomAddress(){
        Random rand = new Random();
        int house_number = 0;
        while (house_number % 100 == 0|| house_number % 10 != 0){
            house_number = rand.nextInt(2000);
        }
        return house_number + " " + directions[rand.nextInt(2)] + " " + rand.nextInt(20) + "\n";

    }

    public void generateAddresses() throws IOException {
        File file = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < 100; i++){
            writer.write(createRandomAddress());
        }
        writer.close();
    }
}
