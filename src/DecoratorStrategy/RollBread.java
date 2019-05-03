package DecoratorStrategy;

public class RollBread extends Sandwich{

    public RollBread(){
        description = "Roll";
    }
    @Override
    public double cost() {
        return 0.75;
    }

    @Override
    public double duration() {
        return 30;//seconds
    }
}
