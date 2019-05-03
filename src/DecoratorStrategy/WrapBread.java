package DecoratorStrategy;

public class WrapBread extends Sandwich{

    public WrapBread(){
        description = "Wrap";
    }
    @Override
    public double cost() {
        return 0.50;
    }

    @Override
    public double duration() {
        return 75;//seconds
    }
}
