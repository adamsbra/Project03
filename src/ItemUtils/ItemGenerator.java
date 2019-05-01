package ItemUtils;

import java.io.*;
import java.util.ArrayList;

public class ItemGenerator {

    public static ArrayList<Item> sandwhich = new ArrayList<>();
    public static ArrayList<Item> drinks = new ArrayList<>();
    public static ArrayList<Item> chips = new ArrayList<>();

    public static void itemInit(String filename) throws IOException {
        File file = new File(filename);
        //Creates a buffered writer
        BufferedReader r = new BufferedReader(new FileReader(file));
        String line;
        while ((line = r.readLine()) != null) {
            String[] args = line.split(",");
            Item item = new Item(args[0], ItemType.valueOf(args[3]), Integer.valueOf(args[1]), Float.valueOf(args[2]));
            if (ItemType.valueOf(args[3]).equals(ItemType.SANDWHICH)) {
                sandwhich.add(item);
            }
        }
        //Close writer
        r.close();
    }




}
