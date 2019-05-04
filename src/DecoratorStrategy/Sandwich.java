package DecoratorStrategy;

public abstract class Sandwich {

    String description;

    public abstract double cost();

    public abstract double duration();

    public String getDescription(){
        return description;
    }
}
