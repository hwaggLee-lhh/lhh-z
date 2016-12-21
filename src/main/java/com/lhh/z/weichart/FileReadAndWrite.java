package com.lhh.z.weichart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileReadAndWrite {
	private static String filePath = FileReadAndWrite.class.getResource("").getPath();
	public static void writeFile(String fileName, String sets)  
            throws IOException {  
        FileWriter fw = new FileWriter(filePath+"/"+fileName);  
        PrintWriter out = new PrintWriter(fw);  
        out.write(sets);  
        out.println();  
        fw.close();  
        out.close();  
    }  
  
    public static String readFile(String fileName) {  
        File file = new File(filePath+"/"+fileName);  
        BufferedReader reader = null;  
        String laststr = "";  
        try {  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            while ((tempString = reader.readLine()) != null) {  
                laststr = laststr + tempString;  
            }  
            reader.close();  
        } catch(FileNotFoundException e) {
        	return null;
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
        return laststr;  
    }  
}
