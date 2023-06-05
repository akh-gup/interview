package se.akh.gup.klarna.questions;


import java.util.*;

class Smoothie {

    public static String ingredients(String order) {

        Smoothie smoothie = new Smoothie();

        smoothie.validateUserInput(order);
        return smoothie.fetchSmoothieIngrendients(order);
    }

    /****
     * Validate if input is not null/empty. Throws Illegal Argument Exception if invalid input detected.
     * @InputParam order: Order selected by user.
     */
    private void validateUserInput(String order){

        if(null == order
                || "".equals(order)
                || order.trim().equals("")){

            throw new IllegalArgumentException("Menu option is not selected. Please select one of these options: Classic, Freezie, Greenie or Just Desserts.");
        }
    }


    /***
     * Fetches smotthie ingredients for the ordered smoothie type.
     * @param order: Order selected by user.
     *
     */
    private String fetchSmoothieIngrendients(String order){

        String[] orderList = order.split(",");

        // Fetch all applicable ingredients to selected smoothie.
        ArrayList<String> allIngredients = SmoothieType.smoothieIngredients(orderList[0]);

        // Remove allergic ingredients from the allIngredients list.
        ArrayList<String> smoothieIngredients = removeAllergicIngredients(orderList, allIngredients);
        Collections.sort(smoothieIngredients);

        // Convert list to comma separated string
        return mapListToString(smoothieIngredients);
    }



    /***
     *  Converts list of ingredients to comma separted string
     @@param smoothieIngredientsList: List of applicable ingredients for smoothie.
     @@return Comma separated value of ingredients.
     */
    private String mapListToString(ArrayList<String> smoothieIngredientsList){

        StringBuilder sb = new StringBuilder();

        for(String ing: smoothieIngredientsList){
            if (!sb.toString().equals("")){
                sb.append(",");
            }
            sb.append(ing);
        }
        return sb.toString();
    }


    /***
     * Removes allergic items from all ingredients appliacble to smoothie.
     @param orderList: order value in string array
     @param allIngredients: All applicable ingredients to selected smoothie
     @return applicable ingredients to smoothie after allergic items removal
     */
    private ArrayList<String> removeAllergicIngredients(String[] orderList, ArrayList<String> allIngredients){

        for (int i=1; i<orderList.length; i++){

            String allergens;

            if (!orderList[i].startsWith("-")){
                throw new IllegalArgumentException("New items addition is not supported.");

            } else {
                allergens = orderList[i].replace("-", "");
            }
            allIngredients.remove(allergens);
        }
        return allIngredients;
    }
}


interface SmoothieType {
    public ArrayList<String> getIngredients();

    static ArrayList<String> smoothieIngredients(String smoothieType){

        ArrayList<String> smoothieIngredients = null;

        // Select type of smoothie based on user input and get all ingredients applicable for that smoothie.
        if("Classic".equals(smoothieType)){
            smoothieIngredients = new ClassicSmoothie().getIngredients();

        } else if ("Freezie".equals(smoothieType)) {
            smoothieIngredients = new FreezieSmoothie().getIngredients();

        } else if ("Greenie".equals(smoothieType)){
            smoothieIngredients = new GreenieSmoothie().getIngredients();

        } else if ("Just Desserts".equals(smoothieType)){
            smoothieIngredients = new JustDessertsSmoothie().getIngredients();

        } else {

            throw new IllegalArgumentException("Only Classic, Freezie, Greenie and Just Desserts smoothies are supported. Please select one of these.");
        }
        return smoothieIngredients;
    }
}

class ClassicSmoothie implements SmoothieType {

    private final ArrayList<String>ingredients = new ArrayList<>(Arrays.asList("banana", "honey", "mango", "peach", "pineapple", "strawberry"));
    @Override
    public ArrayList<String> getIngredients(){
        return ingredients;
    }

}
class FreezieSmoothie implements SmoothieType {

    private final ArrayList<String> ingredients = new ArrayList<>(Arrays.asList("blackberry", "blueberry", "black currant", "grape juice", "frozen yogurt"));
    @Override
    public ArrayList<String> getIngredients(){
        return ingredients;
    }

}
class GreenieSmoothie implements SmoothieType {

    private final ArrayList<String> ingredients = new ArrayList<>(Arrays.asList("green apple", "lime", "avocado", "spinach", "ice", "apple juice"));
    @Override
    public ArrayList<String> getIngredients(){
        return ingredients;
    }

}
class JustDessertsSmoothie implements SmoothieType {

    private final ArrayList<String> ingredients = new ArrayList<>(Arrays.asList("banana", "ice cream", "chocolate", "peanut", "cherry"));
    @Override
    public ArrayList<String> getIngredients(){
        return ingredients;
    }
}
