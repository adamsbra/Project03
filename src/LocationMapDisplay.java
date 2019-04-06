/*
Author : Team Null
Gui for the map and shows moving truck with it's orders' addresses.
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LocationMapDisplay extends JPanel {

    public String[][] map;
    public Truck truck;
    public Location nextLocation;
    public JFrame window;
    private int x;
    private int y;
    private int width;
    private int height;
    private int default_width;
    private int default_height;
    private int square_size = 8;
    final int STEP_SIZE = 200;
    public boolean draw;
    public ArrayList<Location> loc_array = new ArrayList<>();

    //Constructor
    public LocationMapDisplay(int x, int y, int width, int height, SimulationDriver route, Truck truck, boolean draw) {
        map = new String[x][y];
        updateLocations(route);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.truck = truck;
        if (draw) {
            drawGrid();
        }
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
    private void updateLocations(SimulationDriver route) {
        for (Location l : route.locations) {
            int east = l.east;
            int south = l.south;
            this.map[east][south] = "h";
        }
    }

    //Prints locations without the GUI
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
        g.fillRect(truck.getLocation().east * square_size, truck.getLocation().south * square_size, square_size, square_size);
        g.setColor(Color.GREEN);
        g.fillRect(nextLocation.east * square_size, nextLocation.south * square_size, square_size, square_size);
    }

    public void repaintTask(){
        repaint();
    }
}

