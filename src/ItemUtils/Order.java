package ItemUtils;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Order {

    public ArrayList<Item> items;
    public int prep = 0;
    public float price = 0;
    public boolean isPrepped;
    public LocalTime time;
    public LocalTime readyTime;

    public Order(ArrayList<Item> items, LocalTime time){
        this.items = items;
        this.time = time;
        recalculate();
    }

    public void addItem(Item item){
        items.add(item);
        recalculate();
    }

    public void removeItem(Item item){
        if (items.contains(item)){
            items.remove(item);
        }
        recalculate();
    }

    public float calculatePrice(){
        float price = 0;
        for (Item item: items){
            price += item.price;
        }
        return price;
    }

    public int calculatePrep(){
        int prep = 0;
        for (Item item: items){
            prep += item.prep;
        }
        return prep;
    }

    public String toString(){
        String orderString = "";
        for (Item item: items){
            orderString += item.name + " ";
        }
        return orderString;
    }

    public void recalculate(){
        this.prep = calculatePrep();
        this.price = calculatePrice();
    }

    public void getReadyTime(){
        this.readyTime = time.plus(prep, ChronoUnit.MINUTES);
    }

    public boolean isReady(LocalTime time){
        if (time.compareTo(this.readyTime) >= 0){
            return true;
        }
        else{
            return false;
        }
    }

}
