package se.akh.gup.benify.mapper;

import se.akh.gup.benify.entity.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    /***
     *   Map csv file record to order object        *
     *   @param fileData     File Rows as list of string array
     *   @return             Order list
     */
    public List<Order> mapOrders(List<String[]> fileData){

        List<Order> orderList = new ArrayList<>();
        for(String[] orderRecord: fileData){

            Order order = new Order();

            order.setProductId(orderRecord[0]);
            order.setDeliveryArea(orderRecord[1]);
            order.setProductName(orderRecord[2]);
            order.setQuantity(Integer.parseInt(orderRecord[3]));
            order.setProductBrand(orderRecord[4]);

            orderList.add(order);
        }
        return orderList;
    }
}
