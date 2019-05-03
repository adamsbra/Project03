package DecoratorStrategy;

public class Tomato extends Sandwich{
    Sandwich sandwich;

    public Tomato(Sandwich sandwich){
        this.sandwich = sandwich;
    }

    @Override
    public double cost() {
        return 0.75 + sandwich.cost();
    }

    @Override
    public double duration() {
        return 35 + sandwich.duration(); //seconds
    }

    @Override
    public String getDescription() {
        return sandwich.getDescription() + ", Tomato";
    }
}
