package DecoratorStrategy;

import java.util.ArrayList;

public class Orders {

    private static final double TAX = .10;

    private double orderCost        = 0;
    private double orderDuration    = 0;
    private String orderDescription = "";


    //Takes an ArrayList of orders, loop through them to get the bread and condiments, and calls buildSandich()
    //which returns a sandwich object, which is used to get the total cost and duration of the whole order.
    public void buildOrder(ArrayList<String> orders){

        for(int i = 0; i < orders.size(); i++) {

            String order = orders.get(i);

            String[] condiment = order.split(",");

            String bread = condiment[0];

            Sandwich sandwich = buildSandwich(bread, condiment);

            orderCost        += sandwich.cost();
            orderDuration    += sandwich.duration();
            orderDescription += "Sandwich " + i + 1 + ": " + sandwich.getDescription() + ", ";
        }
        orderCost += orderCost + (orderCost*TAX);//adds taxes to the order.
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

    //Builds each sandwich and return a Sandwich object to buildOrder
    private Sandwich buildSandwich(String bread, String[] condiments){
        Sandwich sandwich;

        if(bread.equals("roll")){
            sandwich = new RollBread();
        }
        else {
            sandwich = new WrapBread();
        }

        for(int i = 1; i < condiments.length; i++){

            if(condiments[i].equals("ham")){
                sandwich = new Ham(sandwich);
            }
            else if(condiments[i].equals("turkey")){
                sandwich = new Turkey(sandwich);
            }
            else if(condiments[i].equals("mayonnaise")){
                sandwich = new Mayonnaise(sandwich);
            }
            else if(condiments[i].equals("mustard")){
                sandwich = new Mustard(sandwich);
            }
            else if(condiments[i].equals("cheese")){
                sandwich = new Cheese(sandwich);
            }
            else if(condiments[i].equals("lettuce")){
                sandwich = new Lettuce(sandwich);
            }
            else if(condiments[i].equals("tomato")){
                sandwich = new Tomato(sandwich);
            }
        }
        sandwich = new PaperCover(sandwich); //Adds paper cover to sandwich
        sandwich = new OrderBagging(sandwich); //Bags the sandwich

        return sandwich;
    }


}
