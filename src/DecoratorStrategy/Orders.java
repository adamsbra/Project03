package DecoratorStrategy;

import java.util.ArrayList;

public class Orders {

//    private static String filename = "order.txt";

//    public void getOrder() throws FileNotFoundException {
//        ArrayList condiments = new ArrayList();
//
//        Scanner sc = new Scanner(new FileInputStream(filename));
//
//        while(sc.hasNext()){
//            String line[] = sc.nextLine().split(" ");
//
//
//            for(int i = 0; i < line.length; i++){
////                List sandwiches = new ArrayList();
//                String[] sandwiches = line[i].split(",");
//                String bread = sandwiches[0];
//
//                for(int j = 1; j < sandwiches.length; j++){
//                    condiments.add(sandwiches[j]);
//                }
//                //buildSandwich(bread, condiments);
//            }
////            String bread = line[0];
////
////            for(int i = 0; i < line.length; i++){
////                condiments.add(line[i]);
////            }
////            buildSandwich(bread, condiments);
//        }
//    }

    public Sandwich buildSandwich(String bread, String[] condiments){
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
        System.out.println(sandwich.getDescription() + ", Cost: " + sandwich.cost() + ", Duration: " + sandwich.duration());
        return sandwich;
    }

    public void buildOrder(ArrayList<String> orders){
        double orderCost     = 0;
        double orderDuration = 0;

        for(int i = 0; i < orders.size(); i++) {

            String order = orders.get(i);

            String[] condiment = order.split(",");

            String bread = condiment[0];

            Sandwich sandwich = buildSandwich(bread, condiment);

            orderCost     += sandwich.cost();
            orderDuration += sandwich.duration();

        }
        System.out.println("Total cost: " + orderCost + ", Total duration: " + orderDuration);

    }


//    public static void main(String[] args) {
//
//        ArrayList<String> orders = new ArrayList<>();
//        orders.add("roll,ham,mayonnaise,lettuce");
//        orders.add("wrap,turkey,mustard,cheese,lettuce,tomato");
//
//        Orders o = new Orders();
//        o.buildOrder(orders);
//    }


}
