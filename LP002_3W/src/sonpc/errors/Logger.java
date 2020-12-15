/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.errors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author ACER
 */
public class Logger implements Serializable {

    //readFile() : List<String>
    //writeFile(?) : void
    private final String FILENAME = "LogFile.txt";

    public List<String> readFile() {
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;
        List<String> exceptionList = null;
        try {

            file = new File(FILENAME);
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line = "";

            while ((line = br.readLine()) != null) {
                if (exceptionList == null) {
                    exceptionList = new ArrayList<>();
                }
                exceptionList.add(line);
            }
            br.close();
            fr.close();

        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exceptionList;
    }

    public void writeFile(List<String> exceptionList) {
        File file = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {

            file = new File(FILENAME);
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            
            if(exceptionList != null){
                for (String line : exceptionList){
                    bw.write("\n");
                    bw.write(line);
                    bw.write("\n");
                }
            }
            bw.close();
            fw.close();
            
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
//    public static void main(String[] args) {
//        Logger logger = new Logger();
//        List<String> exceptionList = new ArrayList<>();
//        exceptionList.add("123222");
//        exceptionList.add("123222");
//        exceptionList.add("123222");
//        exceptionList.add("123222");
//        logger.writeFile(exceptionList);
//    }
}
