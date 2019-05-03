package DecoratorStrategy;

public class Lettuce extends Sandwich {
    Sandwich sandwich;

    public Lettuce(Sandwich sandwich){
        this.sandwich = sandwich;
    }

    @Override
    public double cost() {
        return .50 + sandwich.cost();
    }

    @Override
    public double duration() {
        return 20 + sandwich.duration(); //seconds
    }

    @Override
    public String getDescription() {
        return sandwich.getDescription() + ", Lettuce";
    }
}
