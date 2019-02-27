import java.io.IOException;

public class main {

    public static void main(String[] args){
        AddressGenerator generator = new AddressGenerator();
        try {
            generator.generateAddresses();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
