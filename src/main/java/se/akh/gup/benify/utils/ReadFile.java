package se.akh.gup.benify.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    private static final String SEPARATOR = ",";

    /***
     * Read file from the filename and converts it to list of string array
     * @param fileName   Name of file
     * @return           List of all records in file
     */
    public static List<String[]> readFile(String fileName){

        if (fileName.endsWith(".csv")){

            List<String[]> fileData = new ArrayList<>();
            BufferedReader bufferedReader = null;

            try{
                bufferedReader = new BufferedReader(new FileReader(fileName));
                String str;

                while((str = bufferedReader.readLine()) != null){
                    fileData.add(str.split(SEPARATOR));
                }

                return fileData;

            } catch(FileNotFoundException ex){

                System.err.println("File Not found. Exception: " + ex.getMessage());

            } catch(IOException ex){

                System.err.println("Failed to read file. Close the file properly or access issue. Exception: " + ex.getMessage());

            } finally{
                if (bufferedReader != null){
                    try{
                        bufferedReader.close();
                    } catch(IOException ex){
                        System.err.println("Failed to read file. Close the file properly or access issue. Exception: " + ex.getMessage());
                    }
                }
            }
        } else {
            System.err.println("Invalid File Type.");
        }
        return null;
    }
}
