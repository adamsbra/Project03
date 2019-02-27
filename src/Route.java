import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Route {

    public PriorityQueue<Location> locations = new PriorityQueue<>();


    public void addRoutes(String filename){
        try {
            Scanner scnr = new Scanner(filename);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
