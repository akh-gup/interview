package se.akh.gup.benify.calc;

import se.akh.gup.benify.entity.Order;
import se.akh.gup.benify.entity.ProductBrandOrders;
import java.util.ArrayList;
import java.util.List;

public class PopularProductBrand {


    /***
     * Calculates popular brand for each product
     */
    public List<ProductBrandOrders> fetchPopularProductBrands(List<Order> orders){

        List<ProductBrandOrders> popularBrandOrderList = new ArrayList<>();
        List<ProductBrandOrders> popularBrandList = new ArrayList<>();

        // Create list of products having their brand and respective number of orders
        for (Order order: orders){

            createPopularBrandOrderList(order, popularBrandOrderList);
        }

        // Calculate highest product-brand orders
        for (ProductBrandOrders pB: popularBrandOrderList){

            updatePopularBrandForOrder(pB, popularBrandList);
        }
        return popularBrandList;
    }


    /***
     * Updates list of product brand based on existing list.
     *
     */
    private void updatePopularBrandForOrder(ProductBrandOrders pB, List<ProductBrandOrders> popularBrandList){

        boolean productExists = false;

        for (ProductBrandOrders pB1: popularBrandList){

            if (pB1.getProductName().equals(pB.getProductName())){

                if(pB.getNumOfOrders() > pB1.getNumOfOrders()){
                    pB1.setNumOfOrders(pB.getNumOfOrders());
                    pB1.setBrandName(pB.getBrandName());
                }
                productExists = true;
                break;
            }
        }
        if (!productExists){
            popularBrandList.add(pB);
        }
    }


    /***
     *
     *   Creates Popular brand object lists (Sums number of orders)
     *
     */
    private void createPopularBrandOrderList(Order order, List<ProductBrandOrders> popularBrandOrders){

        boolean productExists = false;
        for(ProductBrandOrders popularBrand: popularBrandOrders){

            if (popularBrand.getBrandName().equals(order.getProductBrand()) && popularBrand.getProductName().equals(order.getProductName())){

                popularBrand.setNumOfOrders(popularBrand.getNumOfOrders()+1);
                productExists = true;

                break;
            }
        }

        if (!productExists){

            ProductBrandOrders popularBrand= new ProductBrandOrders();

            popularBrand.setBrandName(order.getProductBrand());
            popularBrand.setProductName(order.getProductName());
            popularBrand.setNumOfOrders(1);

            popularBrandOrders.add(popularBrand);
        }
    }
}
