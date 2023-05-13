package se.akh.gup.benify.calc;

import se.akh.gup.benify.entity.Order;
import java.util.HashMap;
import java.util.List;

public class AverageProductQuantity {

    /***
     * Calcualtes average of product ordered in whole list of orders
     * @param orderList      List of orders placed by customer
     * @return               Product name and average quantity map
     */
    public HashMap<String, Double> calcAvgProductQty(List<Order> orderList){

        HashMap<String, Double> avgProductQtyMap = new HashMap<>();
        int totalOrders = orderList.size();

        for(Order order: orderList){
            avgProductQtyMap.merge(order.getProductName(), (double) order.getQuantity(), Double::sum);
        }

        avgProductQtyMap.replaceAll((k, v) -> (v / totalOrders));

        return avgProductQtyMap;
    }
}
