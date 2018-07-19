package com.Service.WriteInfo;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteInfoToFile {

    /** The method takes a file and a string at the input and writes this line to this file*/
    public static void writeToFile(File file, String line){
        FileWriter fileWriter=null;
        try {
            fileWriter=new FileWriter(file,true);
            fileWriter.write(line);
            fileWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** The method takes an input file and checks to see if it exists, if it exists, it deletes it*/
    public static void deleteFile(File file){
        if(file.exists()){
            file.delete();
        }
    }

}
