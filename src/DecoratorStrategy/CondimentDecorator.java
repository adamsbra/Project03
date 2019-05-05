package DecoratorStrategy;

/*
    AUTHOR: Francis Severino
    Team Null
    Allows for Decorative Strategy to add condiments to the sandwich.
 */
public abstract class CondimentDecorator extends Sandwich {
    public abstract String getDescription();
}

class Ham extends CondimentDecorator {
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


class Turkey extends CondimentDecorator{
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

class Mayonnaise extends Sandwich {
    Sandwich sandwich;

    public Mayonnaise(Sandwich sandwich){
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
        return sandwich.getDescription() + ", Mayonnaise";
    }
}

class Mustard extends Sandwich{
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

class Cheese extends Sandwich {
    Sandwich sandwich;

    public Cheese(Sandwich sandwich){
        this.sandwich = sandwich;
    }

    @Override
    public double cost() {
        return 0.75 + sandwich.cost();
    }

    @Override
    public double duration() {
        return 40 + sandwich.duration(); //seconds
    }

    @Override
    public String getDescription() {
        return sandwich.getDescription() + ", Cheese";
    }
}

class Lettuce extends Sandwich {
    Sandwich sandwich;

    public Lettuce(Sandwich sandwich){
        this.sandwich = sandwich;
    }

    @Override
    public double cost() {
        return .50 + sandwich.cost();
    }

    @Override
    public double duration() {
        return 20 + sandwich.duration(); //seconds
    }

    @Override
    public String getDescription() {
        return sandwich.getDescription() + ", Lettuce";
    }
}

class Tomato extends Sandwich{
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

class OrderBagging extends CondimentDecorator {

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

class PaperCover extends CondimentDecorator {

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

