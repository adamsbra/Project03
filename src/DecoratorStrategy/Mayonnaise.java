package DecoratorStrategy;

public class Mayonnaise extends Sandwich {
    Sandwich sandwich;

    public Mayonnaise(Sandwich sandwich){
        this.sandwich = sandwich;
    }

    @Override
    public double cost() {
        return 0.25 + sandwich.cost();
    }

    @Override
    public double duration() {
        return 30 + sandwich.duration(); //seconds
    }

    @Override
    public String getDescription() {
        return sandwich.getDescription() + ", Mayonnaise";
    }
}
