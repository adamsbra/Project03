package ItemUtils;

public class Item {

    public String name;
    public ItemType type;
    public float price;
    public int prep;

    public Item(String name, ItemType type, int prep, float price){
        this.name = name;
        this.type = type;
        this.prep = prep;
        this.price = price;
    }
}
