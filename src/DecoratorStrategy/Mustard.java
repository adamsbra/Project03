package DecoratorStrategy;

public class Mustard extends Sandwich{
    Sandwich sandwich;

    public Mustard(Sandwich sandwich){
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
        return sandwich.getDescription() + ", Mustard";
    }
}
