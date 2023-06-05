package se.akh.gup.benify.calc;

import se.akh.gup.benify.entity.Order;
import se.akh.gup.benify.entity.ProductBrandOrders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopularProductBrand {


    /***
     * Calculates popular brand for each product
     */
    public List<ProductBrandOrders> fetchPopularProductBrands(List<Order> orders){


        // Approach #01
        HashMap<String, HashMap<String, Integer>> productBrandMap = new HashMap<>();
        for(Order order: orders){
            populateAllProductOrders(order, productBrandMap);
        }

        HashMap<String, String> result = new HashMap<>();
        for (Map.Entry<String, HashMap<String,Integer>> productEntry: productBrandMap.entrySet()){
            result.put(productEntry.getKey(), fetchPopularBrandForProduct(productEntry.getValue()));
        }


        // Approach #2
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
        System.out.println(popularBrandList);
        return popularBrandList;
    }


    /****
     *
     *
     *
     *
     */
    private void populateAllProductOrders(Order order, HashMap<String, HashMap<String, Integer>> productBrandMap){

        HashMap<String, Integer> brandOrderMap = new HashMap<>();
        boolean brandOrderExists = false;

        if (productBrandMap.get(order.getProductName()) != null){

            brandOrderMap = productBrandMap.get(order.getProductName());

            for (Map.Entry<String, Integer> entry: brandOrderMap.entrySet()){

                if (entry.getKey().equals(order.getProductBrand())){

                    brandOrderMap.put(order.getProductBrand(), entry.getValue() + 1);
                    brandOrderExists = true;
                    break;
                }
            }
        }

        if (!brandOrderExists){
            brandOrderMap.put(order.getProductBrand(), 1);
        }
        productBrandMap.put(order.getProductName(), brandOrderMap);
    }


    /***
     *
     *
     */
    private String fetchPopularBrandForProduct(HashMap<String, Integer> brandOrderMap){
        int maxVal = Integer.MIN_VALUE;
        String popularBrand = "";

        for(Map.Entry<String, Integer> brandEntry: brandOrderMap.entrySet()){

            if (maxVal < brandEntry.getValue()) {
                maxVal = brandEntry.getValue();
                popularBrand = brandEntry.getKey();
            }
        }
        return popularBrand;
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
