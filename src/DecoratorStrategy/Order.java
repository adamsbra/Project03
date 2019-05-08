package DecoratorStrategy;

public class Order {



    private double orderCost;
    private double orderDuration;
    private String orderDescription;

    public Order(double orderCost, double orderDuration, String orderDescription){
        this.orderCost = orderCost;
        this.orderDuration = orderDuration;
        this.orderDescription = orderDescription;
    }

    //Getter for the whole order's cost
    public double getOrderCost(){
        return orderCost;
    }

    //Getter for the whole order's duration
    public double getOrderDuration(){
        return orderDuration;
    }

    //Getter for the whole order's description
    public String getOrderDescription(){
        return orderDescription;
    }

    public String toString(){
        return "Order Description: " + orderDescription + " Cost: $" + orderCost + " Prep Time: " + orderDuration + " seconds";
    }
}
