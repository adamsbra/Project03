package DecoratorStrategy;

public class PaperCover extends CondimentDecorator {

    Sandwich sandwich;

    public PaperCover(Sandwich sandwich){
        this.sandwich = sandwich;
    }
    @Override
    public double cost() {
        return 0.50;
    }

    @Override
    public double duration() {
        return 75;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
