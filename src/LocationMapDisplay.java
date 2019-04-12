import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TimerTask;

public class LocationMapDisplay extends JPanel implements Observer {

    public String[][] map;
    public Location truckLocation;
    public Location nextLocation;
    public JFrame window;
    private int x;
    private int y;
    private int width;
    private int height;
    private int square_size = 8;

    public LocationMapDisplay(int x, int y, int width, int height, Route route) {
        map = new String[x][y];
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        updateLocations(route);
        drawGrid();
        repaint();
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
    private void updateLocations(Route route) {
        for (Location location: route.houseLocations) {
            int east = location.east;
            int south = location.south;
            this.map[east][south] = "h";
        }
    }

    public void printLocations() {
        for (int i = 0; i < this.height; i++) {
            if (i < 10) {
                System.out.print("00");
            }
            if (i >= 10 && i < 100) {
                System.out.print("0");
            }
            System.out.print(i + "0");
            for (int j = 0; j < this.width; j++) {
                System.out.print(this.map[i][j]);
            }
            System.out.println();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                g.drawRect(i * square_size, j * square_size, square_size, square_size);

        g.setColor(Color.BLUE);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (map[i][j] != null) {
                    if (map[i][j].equals("h")) {
                        g.fillRect(i * square_size, j * square_size, square_size, square_size);
                    }
                }
            }
        }
        g.setColor(Color.BLACK);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (i % 10 == 0) {
                    g.fillRect(i * square_size, j * square_size, square_size / 2, square_size);
                } else if (i % 10 == 0 || j % 10 == 0) {
                    g.fillRect(i * square_size, j * square_size, square_size, square_size / 2);
                }
            }
        }
        g.setColor(Color.MAGENTA);
        g.fillRect(truckLocation.east * square_size, truckLocation.south * square_size, square_size, square_size);
        g.setColor(Color.GREEN);
        g.fillRect(nextLocation.east * square_size, nextLocation.south * square_size, square_size, square_size);
    }

    @Override
    public void updateGui(Location truckLocation, Location nextLocation) {
        this.truckLocation = truckLocation;
        this.nextLocation = nextLocation;
        repaint();
    }
}

