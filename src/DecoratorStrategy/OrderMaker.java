package DecoratorStrategy;
/*
Author: Francis Severino
Team Null
This class allows for orders objects which is used to build orders of sandwiches and get their price, duration and description
*/

import java.util.ArrayList;
import java.util.Arrays;


public class OrderMaker {

    private static final double TAX = .10;

    //Takes an ArrayList of orders, loop through them to get the bread and condiments, and calls buildSandich()
    //which returns a sandwich object, which is used to get the total cost and duration of the whole order.
    public static Order buildOrder(ArrayList<String> orders){

        double orderCost = 0;
        double orderDuration = 0;
        String orderDescription = "";

        for(int i = 0; i < orders.size(); i++) {

            String order = orders.get(i);

            //Changed these assignments so that we start with ingredients and get the bread and condiments individually.
            //Originally, bread was included in the condiments so now they are seperate.
            String[] ingredients = order.split(",");

            String bread = ingredients[0];

            String[] condiment = Arrays.copyOfRange(ingredients, 1, ingredients.length);

            Sandwich sandwich = buildSandwich(bread, condiment);

            orderCost        += sandwich.cost();
            orderDuration    += sandwich.duration();
            orderDescription += "Sandwich " + (i + 1) + ": " + sandwich.getDescription() + "\n";
        }
        orderCost += orderCost + (orderCost*TAX);//adds taxes to the order.
        return new Order(orderCost, orderDuration, orderDescription);
        //System.out.println(orderDescription + "   " + orderCost);
    }




    //Builds each sandwich and return a Sandwich object to buildOrder
    private static Sandwich buildSandwich(String bread, String[] condiments){
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
