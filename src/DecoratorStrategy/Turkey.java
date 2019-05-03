package DecoratorStrategy;

public class Turkey extends CondimentDecorator{
    Sandwich sandwich;

    public Turkey(Sandwich sandwich){
        this.sandwich = sandwich;
    }

    @Override
    public double cost() {
        return 1.25 + sandwich.cost();
    }

    @Override
    public double duration() {
        return 30 + sandwich.duration(); //seconds
    }

    @Override
    public String getDescription() {
        return sandwich.getDescription() + ", Turkey";
    }
}
