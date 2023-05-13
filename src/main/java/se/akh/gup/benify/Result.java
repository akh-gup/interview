package se.akh.gup.benify;

import se.akh.gup.benify.calc.AverageProductQuantity;
import se.akh.gup.benify.calc.PopularProductBrand;
import se.akh.gup.benify.entity.Order;
import se.akh.gup.benify.entity.ProductBrandOrders;
import se.akh.gup.benify.mapper.OrderMapper;
import se.akh.gup.benify.utils.ReadFile;
import se.akh.gup.benify.utils.WriteFile;
import java.util.HashMap;
import java.util.List;

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

        // Calculate average quantity of products ordered.
        HashMap<String, Double> productAvgSize = new AverageProductQuantity().calcAvgProductQty(orders);
        WriteFile.writeAverageProductQuantity(averageProductQtyFileName, productAvgSize);

        // Calculate Popular Brand for each product based on number of orders.
        List<ProductBrandOrders> highestOrderedBrand = new PopularProductBrand().fetchPopularProductBrands(orders);
        WriteFile.writePopularBrand(popularProductBrandFileName, highestOrderedBrand);
    }
}
