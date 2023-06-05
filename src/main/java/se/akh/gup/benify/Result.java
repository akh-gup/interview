package se.akh.gup.benify;

import se.akh.gup.benify.calc.AverageProductQuantity;
import se.akh.gup.benify.calc.PopularProductBrand;
import se.akh.gup.benify.entity.Order;
import se.akh.gup.benify.entity.ProductBrandOrders;
import se.akh.gup.benify.mapper.OrderMapper;
import se.akh.gup.benify.utils.ReadFile;
import se.akh.gup.benify.utils.WriteFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {

    /*
     * Complete the 'generateFiles' function below.
     *
     * The function accepts STRING input_file_name as parameter.
     */

    public static void generateFiles(String input_file_name) {

        String averageProductQtyFileName = "0_" + input_file_name;
        String popularProductBrandFileName = "1_" + input_file_name;

        // Reads input file data
        List<String[]> fileData = ReadFile.readFile(input_file_name);
        if (fileData == null || fileData.isEmpty()){
            return;
        }

        // Converts input file data to orders list.
        List<Order> orders = new OrderMapper().mapOrders(fileData);
        if (orders.isEmpty()) {
            return;
        }

        try{
            Result result = new Result();

            result.avgQuantity(averageProductQtyFileName, orders);
            result.popularBrand(popularProductBrandFileName, orders);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /***
     * Calculates average quantity and writes to CSV file
     * @param outputFileName        Output file name.
     * @param orders                List of orders placed by customer.
     */
    private void avgQuantity(String outputFileName, List<Order> orders){

        // Calculate average quantity of products ordered.
        HashMap<String, Double> productAvgSize = new AverageProductQuantity().calcAvgProductQty(orders);
        writeAverageProductQuantity(outputFileName, productAvgSize);
    }


    /***
     *
     * Writes average product quantity to file
     *
     * @param fileName           Output File Name
     * @param avgProductQtyMap   Product Average quantity map
     *
     */
    private void writeAverageProductQuantity(String fileName, HashMap<String, Double> avgProductQtyMap){

        List<String> recordList = new ArrayList<>();

        for (Map.Entry<String, Double> productAvgQty: avgProductQtyMap.entrySet()){
            recordList.add(productAvgQty.getKey() + ',' + productAvgQty.getValue());
        }

        WriteFile.writeLinesToCSV(fileName, recordList);
    }


    /***
     *
     * @param outputFileName        Output file name.
     * @param orders                List of orders placed by customer.
     */
    private void popularBrand(String outputFileName, List<Order> orders){

        // Calculate average quantity of products ordered.
        List<ProductBrandOrders> highestOrderedBrand = new PopularProductBrand().fetchPopularProductBrands(orders);
        writePopularBrand(outputFileName, highestOrderedBrand);
    }


    /***
     *
     * Writes popular brand list to file
     *
     */
    private void writePopularBrand(String fileName, List<ProductBrandOrders> productBrandOrders){

        List<String> recordList = new ArrayList<>();

        for (ProductBrandOrders brandOrder: productBrandOrders){
            recordList.add(brandOrder.getProductName() + ',' + brandOrder.getBrandName());
        }

        WriteFile.writeLinesToCSV(fileName, recordList);
    }
}
