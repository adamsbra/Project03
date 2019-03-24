import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.Charset;

public class LocationMapDisplay extends JComponent {

    private String[][] map;
    private Location truckLocation;
    private int x;
    private int y;
    private int width;
    private int height;
    private int default_width;
    private int default_height;

    public LocationMapDisplay(int x, int y, int width, int height, Route route, Location truckLocation) {
        map = new String[x][y];
        updateLocations(route);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.truckLocation = truckLocation;
        drawGrid();
    }

    //Creates a 2d array with the route
    public void drawGrid() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, width, height);
        window.getContentPane().add(this);
        window.setVisible(true);
    }

    //Changes symbols based on east and south.
    private void updateLocations(Route route) {
        for (Location l : route.locations) {
            int east = l.east;
            int south = l.south;
            this.map[south][east] = "h";
        }
    }

    public void updateTruckLocation(Location truck) {
        truckLocation = truck;
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

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                g.drawRect(i * 4, j * 4, 4, 4);

        g.setColor(Color.BLUE);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (map[i][j] != null) {
                    if (map[i][j].equals("h")) {
                        g.fillRect(i * 4, j * 4, 4, 4);
                    }
                }
            }
        }
        g.setColor(Color.BLACK);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (i % 10 == 0) {
                    g.fillRect(i * 4, j * 4, 2, 4);
                } else if (i % 10 == 0 || j % 10 == 0) {
                    g.fillRect(i * 4, j * 4, 4, 2);
                }
            }
        }
        g.setColor(Color.MAGENTA);
        g.fillRect(truckLocation.south * 4, truckLocation.east * 4, 4, 4);
    }
}

