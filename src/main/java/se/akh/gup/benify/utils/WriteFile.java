package se.akh.gup.benify.utils;

import se.akh.gup.benify.entity.ProductBrandOrders;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteFile {

    /***
     *
     * Write list of records in csv file.
     * Note: Records should be in correct format i.e. comma separated values.
     * @param fileName          Name of the file
     * @param recordsList       List of records to be saved in file
     */

    private static void writeLinesToCSV(String fileName, List<String> recordsList){
        if (fileName.endsWith(".csv")){

            FileWriter fileWriter = null;

            try{
                fileWriter = new FileWriter(fileName);
                for (String record: recordsList){
                    fileWriter.append(record).append("\n");
                }

            } catch(FileNotFoundException ex){

                System.err.println("Unable to find file. Exception: " + ex.getMessage());

            } catch(IOException ex){
                System.err.println("Unable to write data to file. Exception: " + ex.getMessage());

            } finally{
                if (fileWriter != null){
                    try{
                        fileWriter.flush();
                        fileWriter.close();

                    } catch(IOException ex){
                        System.err.println("Unable to save data to file. Exception: " + ex.getMessage());
                    }
                }
            }
        } else {
            System.err.println("Invalid File Type.");
        }
    }
    /***
     * Writes popular brand list to file
     *
     */
    public static void writePopularBrand(String fileName, List<ProductBrandOrders> productBrandOrders){

        List<String> recordList = new ArrayList<>();

        for (ProductBrandOrders brandOrder: productBrandOrders){

            recordList.add(brandOrder.getProductName() + ',' + brandOrder.getBrandName());
        }
        writeLinesToCSV(fileName, recordList);
    }


    /***
     * Writes average product quantity to file
     * @param fileName           Output File Name
     * @param avgProductQtyMap   Product Average quantity map
     */

    public static void writeAverageProductQuantity(String fileName, HashMap<String, Double> avgProductQtyMap){

        List<String> recordList = new ArrayList<>();

        for (Map.Entry<String, Double> productAvgQty: avgProductQtyMap.entrySet()){

            recordList.add(productAvgQty.getKey() + ',' + productAvgQty.getValue());
        }
        writeLinesToCSV(fileName, recordList);
    }
}
