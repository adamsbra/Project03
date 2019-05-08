package DecoratorStrategy;
/*
    AUTHOR: Francis Severino
    Team Null
    create the abstract Sandwich class to implement Decorator Strategy
 */
public abstract class Sandwich {

    String description;

    public abstract double cost();

    public abstract double duration();

    public String getDescription(){
        return description;
    }
}

/*
    Class that extends Sandwich to instantiate the Sandwich object to have a Roll Bread
 */
class RollBread extends Sandwich{

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

/*
    Class that extends Sandwich to instantiate the Sandwich object to have a Wrap Bread
 */
class WrapBread extends Sandwich{

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
