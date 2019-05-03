package DecoratorStrategy;

public class Ham extends CondimentDecorator {
    Sandwich sandwich;

    public Ham(Sandwich sandwich){
        this.sandwich = sandwich;
    }

    @Override
    public double cost() {
        return 1.50 + sandwich.cost();
    }

    @Override
    public double duration() {
        return 30 + sandwich.duration(); //seconds
    }

    @Override
    public String getDescription() {
        return sandwich.getDescription() + ", Ham";
    }
}

