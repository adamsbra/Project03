public class Location{

    public final double BLOCK_DISTANCE = .25;
    public int street_number;
    public int house_number;
    public String direction;

    public Location(int street_number, int house_number, String direction){
        this.street_number = street_number;
        this.house_number = house_number;
        this.direction = direction;
    }

    public int getDistance(Location origin){

    }

    public String toString(){

    }



}