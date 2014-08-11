/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulatorrunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeffery
 */
public class SimulatorResumer {
    private class LogFilter implements FileFilter{

        @Override
        public boolean accept(File pathname) {
            return pathname.getName().endsWith(".log");
        }
        
    }
    
    private String dir = "";
    private File[] listOfFiles;
    private int work_id = 0;
    public SimulatorResumer(String dir){
        this.dir = dir;
        File folder = new File(dir);
        listOfFiles = folder.listFiles(new LogFilter()); 
        work_id = 0;
    }
    
    public String resumeNext(){
        if(work_id < listOfFiles.length){
            try {
                BufferedReader bufferReader = new BufferedReader(new FileReader(listOfFiles[work_id]));
                String line;
                while((line = bufferReader.readLine()) != null){
                    if(line.startsWith("cache size:")){
                        line = line.replaceAll("cache size:", "").replaceAll(" ", "").replaceAll("\n", "");
                        CacheProperty.setProperties("CACHE_SIZE", line);
                    }
                    if(line.startsWith("cache ways:")){
                        line = line.replaceAll("cache ways:", "").replaceAll(" ", "").replaceAll("\n", "");
                        CacheProperty.setProperties("CACHE_WAY", line);
                    }
                    if(line.startsWith("cache policy:")){
                        line = line.replaceAll("cache policy:", "").replaceAll(" ", "").replaceAll("\n", "");
                        CacheProperty.setProperties("CACHE_POLICY", line);
                    }
                    if(line.startsWith("comment: ")){
                        line = line.replaceAll("comment: ", "").replaceAll("\n", "");
                        CacheProperty.setProperties("CACHE_COMMENT", "Size "+CacheProperty.props.getProperty("CACHE_SIZE")+" cache run 2");
                    }
                }
                CacheProperty.storeProperties();
                work_id ++;
                return listOfFiles[work_id-1].getName().replaceAll(".log", "");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SimulatorResumer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SimulatorResumer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return null;
    }
}
