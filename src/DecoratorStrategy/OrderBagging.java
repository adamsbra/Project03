package DecoratorStrategy;

public class OrderBagging extends CondimentDecorator {

    Sandwich sandwich;

    public OrderBagging(Sandwich sandwich){
        this.sandwich = sandwich;
    }

    @Override
    public double cost() {
        return 0.75;
    }

    @Override
    public double duration() {
        return 20;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
