package se.akh.gup.benify.utils;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteFile {

    /***
     *
     * Write list of records in csv file.
     * Note: Records should be in correct format i.e. comma separated values.
     * @param fileName          Name of the file
     * @param recordsList       List of records to be saved in file
     */

    public static void writeLinesToCSV(String fileName, List<String> recordsList){
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
}
