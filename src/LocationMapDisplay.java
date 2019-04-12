import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TimerTask;

public class LocationMapDisplay extends JPanel implements Observer {

    private Integer[][] map;
    private JFrame window;
    private int x;
    private int y;
    private int width;
    private int height;
    private final int SQUARE_SIZE = 8;
    private final int HOUSES_ON_BLOCK = 10;
    private Location truckLocation;
    private Location nextLocation;
    private int currentDuration;
    private PriorityQueue<Location> locations;

    public LocationMapDisplay(int x, int y, int width, int height, Truck truck) {
        map = new Integer[x][y];
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.nextLocation = truck.nextLocation;
        this.currentDuration = truck.currentDuration;
        this.truckLocation = truck.getLocation();
        this.locations = truck.locations;
        updateLocations();
        drawGrid();
    }

    //Creates a 2d array with the route
    public void drawGrid() {
        this.window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, width, height);
        window.getContentPane().add(this);
        window.setVisible(true);
    }

    //Changes symbols based on east and south.
    public void updateLocations() {
        for (Location location: locations) {
            int east = location.east;
            int south = location.south;
            this.map[east][south] = 1;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                g.drawRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        g.setColor(Color.BLACK);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (i % HOUSES_ON_BLOCK == 0) {
                    g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE / 2, SQUARE_SIZE);
                } else if (i % HOUSES_ON_BLOCK == 0 || j % HOUSES_ON_BLOCK == 0) {
                    g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE / 2);
                }
            }
        }
        g.setColor(Color.BLUE);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (map[i][j] != null) {
                    if (map[i][j].equals(1)) {
                        g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    }
                }
            }
        }
        g.setColor(Color.MAGENTA);
        g.fillRect(truckLocation.east * SQUARE_SIZE, truckLocation.south * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        g.setColor(Color.GREEN);
        if (nextLocation != null) {
            g.fillRect(nextLocation.east * SQUARE_SIZE, nextLocation.south * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
    }


    @Override
    public void updateGui(Location truckLocation, Location nextLocation, int currentDuration, PriorityQueue<Location> locations) {
        this.repaint();
        this.truckLocation = truckLocation;
        this.nextLocation = nextLocation;
        this.currentDuration = currentDuration;
        this.locations = locations;
        try {
            Thread.sleep(currentDuration * 200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

