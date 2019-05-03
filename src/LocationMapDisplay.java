import LocationUtils.Location;
import TruckUtils.Truck;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import LocationUtils.LocationsQueue;
import TruckUtils.Observer;

public class LocationMapDisplay extends JPanel implements Observer {

    private final int SQUARE_SIZE = 6;
    private final int HOUSES_ON_BLOCK = 10;
    private Color TRUCK_COLOR = Color.MAGENTA;
    private Color NEXT_COLOR = Color.GREEN;
    private Color HOUSE_COLOR = Color.BLUE;
    private Color ROAD_COLOR = Color.BLACK;
    private int X;
    private int Y;
    private int SLEEP_TICKS = 200;
    private JPanel panel;
    private ArrayList<String> infoArray = new ArrayList<>();
    private Integer[][] map;
    private int width;
    private int height;
    private Location truckLocation;
    private Location nextLocation;
    private int currentStepDuration;
    private LocalTime currentTime;
    private boolean orderFulfilled;
    private PriorityQueue<Location> locations;
    private JTextArea tarea;

    public LocationMapDisplay(int gridSize, int width, int height, Truck truck) {
        //Represents the coordinate system used to construct the houses and roads. X and Y represent width and height
        //respectively.
        X = HOUSES_ON_BLOCK * gridSize + 1;
        Y = HOUSES_ON_BLOCK * gridSize + 1;
        map = new Integer[X][Y];
        //Actual width and height resolution of the window. Not sure how to do this dynamically based on elements in gui,
        // but it isn't a huge concern at the moment.
        this.width = width;
        this.height = height;
        this.nextLocation = truck.getNextLocation();
        this.currentStepDuration = truck.getCurrentStepDuration();
        this.truckLocation = truck.getLocation();
        LocationsQueue locationsQueueInstance = LocationsQueue.getLocationsQueueInstance();
        this.locations = locationsQueueInstance.locationsQueue;
        updateLocations();
        drawGrid();
    }

    //Creates a 2d array with the route
    private void drawGrid() {
        JFrame window = new JFrame();
        //Slider to adjust the simulation speed.
        JSlider s_speed = new JSlider();
        Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
        position.put(0, new JLabel("Fast"));
        position.put(100, new JLabel("Slow"));
        s_speed.setMinorTickSpacing(10);
        s_speed.setPaintTicks(true);
        s_speed.setPaintLabels(true);
        s_speed.setLabelTable(position);
        s_speed.setValue(40);
        s_speed.addChangeListener(e -> {
            SLEEP_TICKS = ((JSlider) e.getSource()).getValue() * 5;
        });
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create the window and the text area for tracker.
        window.setBounds(0, 0, width, height);
        window.getContentPane().add(this);
        window.setVisible(true);
        tarea = new JTextArea();
        tarea.setText(infoArray.toString());
        window.add(tarea, BorderLayout.SOUTH);
        window.add(s_speed, BorderLayout.NORTH);
//        tarea.setLocation(0, Y + 10);
    }

    private void updateLocations() {
        this.map = new Integer[X][Y];
        for (Location location: locations) {
            int east = location.east;
            int south = location.south;
            this.map[east][south] = 1;
        }
    }

    //Currently, tracker is having issues with updating on the map so I've done a crude implementation of it here for
    //the purpose of the display.
    private void setInfoArray(Truck truck){
        infoArray.set(0, "Current Location ~ " + truckLocation.toString());
        infoArray.set(1, "Current Time ~ " + currentTime.toString());
        infoArray.set(2, "Order Completed? ~ " + String.valueOf(orderFulfilled));
        infoArray.set(3, "Distance Traveled ~ " + truck.distance);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw all of the roads as a solid black square first.
//        g.setColor(ROAD_COLOR);
//        for (int i = 0; i < X; i++)
//            for (int j = 0; j < Y; j++)
//                g.drawRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        //Draws roads by using half squares dependent on the direction of the road.
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                //Check if we are on an intersection.
                if (i % HOUSES_ON_BLOCK == 0 && j % HOUSES_ON_BLOCK == 0){
                    g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
                if (i % HOUSES_ON_BLOCK == 0 && j % HOUSES_ON_BLOCK != 0) {
                    g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE / 2, SQUARE_SIZE);
                } else if (i % HOUSES_ON_BLOCK != 0 && j % HOUSES_ON_BLOCK == 0) {
                    g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE / 2);
                }
            }
        }
        //Draws the houses present in the locations queue.
        g.setColor(HOUSE_COLOR);
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                if (map[i][j] != null) {
                    if (map[i][j].equals(1)) {
                        g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    }
                }
            }
        }
        //Draws the truck location on the map.
        g.setColor(TRUCK_COLOR);
        g.fillRect(truckLocation.east * SQUARE_SIZE, truckLocation.south * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        //Draws the next location on the map.
        g.setColor(NEXT_COLOR);
        if (nextLocation != null) {
            g.fillRect(nextLocation.east * SQUARE_SIZE, nextLocation.south * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
    }


    @Override
    public void update(Truck truck) {
        //Weird implementation of the Tracker for the map.
        LocationsQueue locationsQueueInstance = LocationsQueue.getLocationsQueueInstance();
        this.truckLocation = truck.getLocation();
        this.nextLocation = truck.getNextLocation();
        this.currentStepDuration = truck.getCurrentStepDuration();
        this.locations = locationsQueueInstance.locationsQueue;
        this.currentTime = truck.getCurrentTime();
        this.orderFulfilled = truck.atLocation;
        updateLocations();
        if (infoArray.isEmpty()){
            infoArray.add(0, "Current Location ~ " + truckLocation.toString());
            infoArray.add(1, "Current Time ~ " + currentTime.toString());
            infoArray.add(2, "Order Completed? ~ " + String.valueOf(orderFulfilled));
            infoArray.add(3, "Distance Traveled ~ " + String.valueOf(truck.distance));
        }
        else {
            setInfoArray(truck);
        }
        String info = "";
        for (String str: infoArray)
            info += str + " ";
        tarea.setText(info);
        try {
            Thread.sleep(currentStepDuration * SLEEP_TICKS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.repaint();
    }
}

