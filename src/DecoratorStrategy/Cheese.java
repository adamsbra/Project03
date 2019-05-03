package DecoratorStrategy;

public class Cheese extends Sandwich {
    Sandwich sandwich;

    public Cheese(Sandwich sandwich){
        this.sandwich = sandwich;
    }

    @Override
    public double cost() {
        return 0.75 + sandwich.cost();
    }

    @Override
    public double duration() {
        return 40 + sandwich.duration(); //seconds
    }

    @Override
    public String getDescription() {
        return sandwich.getDescription() + ", Cheese";
    }
}
