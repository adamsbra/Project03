
public class LocationMapDisplay {

    String[][] map;
    int width;
    int height;

    //Creates a 2d array with the route
    LocationMapDisplay(int width, int height, Route route){
        map = new String[width][height];
        this.width = width;
        this.height = height;
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if (i % 10 == 0 || j % 10 == 0){
                    map[j][i] = ".";
                }
                else{
                    map[j][i] = " ";
                }
            }
        }
        addLocations(route);

    }

    //Changes symbols based on east and south.
    public void addLocations(Route route){
        for (Location l: route.locations){
            int east = l.east;
            int south = l.south;
            this.map[east][south] = "o";
        }
    }

    public void printLocations(){
        for (int i = 0; i < this.height; i++){
            System.out.print(i + "0");
            for (int j = 0; j < this.width; j++){
                System.out.print(this.map[i][j]);
            }
            System.out.println();
        }
    }


}
