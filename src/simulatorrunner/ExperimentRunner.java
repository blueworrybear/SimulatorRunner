/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulatorrunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.Scanner;
import static simulatorrunner.CacheProperty.props;

/**
 *
 * @author Jeffery
 */
public class ExperimentRunner {
    
    public void wayVsHitrate(int size,String comment) throws IOException, InterruptedException{
        for(int w = 1; w <= size; w *= 2){
            props.put("CACHE_SIZE", String.valueOf(size));
            props.put("CACHE_WAY", String.valueOf(w));
            props.put("CACHE_COMMENT", "ways "+w+" cache : "+comment);
            props.store(new FileOutputStream("config.properties"), "store");
            Process proc = Runtime.getRuntime().exec("java -jar Cache_Simulator.jar");
            SimulatorRunner.copy(proc.getInputStream(), System.out);
            SimulatorRunner.copy(proc.getErrorStream(), System.out);
            proc.waitFor();
        }
    }
    
    public void sizeVsHitrate(String comment) throws IOException, InterruptedException{
        props.put("PARSER", String.valueOf(CacheProperty.LOCAL_PARSER));
        props.put("SWITCH_TYPE", String.valueOf(CacheProperty.SWITCH_SIMPLE));
        props.put("LOAD_TABLE", String.valueOf(CacheProperty.LOAD_ALL));
        props.store(new FileOutputStream("config.properties"), "store");
        for(int port=-1; port <=-1; port+=1){
            props.put("START_PORT", String.valueOf(port));
            props.store(new FileOutputStream("config.properties"), "store");
            for(int c_size = 512; c_size <= 4096; c_size *=2){
                props.put("CACHE_SIZE", String.valueOf(c_size));
                props.put("CACHE_WAY", String.valueOf(1));
                props.put("CACHE_COMMENT", "Size "+c_size+" cache : "+comment);
                props.store(new FileOutputStream("config.properties"), "store");

                Process proc = Runtime.getRuntime().exec("java -jar simulator/Simulator.jar");
                SimulatorRunner.copy(proc.getInputStream(), System.out);
                SimulatorRunner.copy(proc.getErrorStream(), System.out);
                proc.waitFor();
            }
        }
    }
    
    public void sizeVsHitrateTCP(String comment) throws IOException, InterruptedException{
        props.put("PARSER", String.valueOf(CacheProperty.LOCAL_PARSER));
        props.put("SWITCH_TYPE", String.valueOf(CacheProperty.SWITCH_TCP));
        props.put("LOAD_TABLE", String.valueOf(CacheProperty.LOAD_PORT));
        props.store(new FileOutputStream("config.properties"), "store");
        for(int port=26; port <=26; port+=1){
            props.put("START_PORT", String.valueOf(port));
            props.store(new FileOutputStream("config.properties"), "store");
            for(int c_size = 4; c_size <= 128; c_size *=2){
                props.put("CACHE_SIZE", String.valueOf(c_size));
                props.put("CACHE_WAY", String.valueOf(1));
                props.put("CACHE_COMMENT", "Size "+c_size+" cache : "+comment);
                props.store(new FileOutputStream("config.properties"), "store");

                Process proc = Runtime.getRuntime().exec("java -jar simulator/Simulator.jar");
                SimulatorRunner.copy(proc.getInputStream(), System.out);
                SimulatorRunner.copy(proc.getErrorStream(), System.out);
                proc.waitFor();
            }
        }
    }
    
    public void sizeVsHitratePerport(String comment) throws IOException, InterruptedException{
        props.put("PARSER", String.valueOf(CacheProperty.LOCAL_PARSER));
        props.put("SWITCH_TYPE", String.valueOf(CacheProperty.SWITCH_SIMPLE));
        props.put("LOAD_TABLE", String.valueOf(CacheProperty.LOAD_PORT));
        props.store(new FileOutputStream("config.properties"), "store");
        for(int port=1; port <=32; port+=1){
            props.put("START_PORT", String.valueOf(port));
            props.store(new FileOutputStream("config.properties"), "store");
            for(int c_size = 64; c_size <= 128; c_size *=2){
                props.put("CACHE_SIZE", String.valueOf(c_size));
                props.put("CACHE_WAY", String.valueOf(1));
                props.put("CACHE_COMMENT", "Size "+c_size+" cache : "+comment);
                props.store(new FileOutputStream("config.properties"), "store");

                Process proc = Runtime.getRuntime().exec("java -jar simulator/Simulator.jar");
                SimulatorRunner.copy(proc.getInputStream(), System.out);
                SimulatorRunner.copy(proc.getErrorStream(), System.out);
                proc.waitFor();
            }
        }
    }
    
    public void hybrid(String comment) throws IOException, InterruptedException{
        props.put("PARSER", String.valueOf(CacheProperty.HYBRID));
        props.put("LOAD_TABLE", String.valueOf(CacheProperty.LOAD_PORT));
        props.store(new FileOutputStream("config.properties"), "store");
        for(int c_size = 1; c_size <= 4; c_size *=2){
            props.put("CACHE_SIZE", String.valueOf(c_size));
            props.put("CACHE_WAY", String.valueOf(1));
            props.put("CACHE_COMMENT", "Hybrid "+c_size+" cache : "+comment);
            props.store(new FileOutputStream("config.properties"), "store");
            Process proc = Runtime.getRuntime().exec("java -jar Cache_Simulator.jar");
            SimulatorRunner.copy(proc.getInputStream(), System.out);
            SimulatorRunner.copy(proc.getErrorStream(), System.out);
            proc.waitFor();
            if(proc.exitValue() == 1){
                throw new ExportException("Process fail exception.");
            }
        }
    }
    
    public void hybrid_result(String comment) throws IOException, InterruptedException{
        for(int h = 1; h <= 4 ; h*=2){
            props.put("PARSER", String.valueOf(CacheProperty.HYBRID_RESULT));
            props.put("LOAD_TABLE", String.valueOf(CacheProperty.LOAD_ALL));
            props.put("HYBRID_RESULT", String.valueOf(h));
            props.store(new FileOutputStream("config.properties"), "store");
            for(int c_size = 64; c_size <= 128; c_size *=2){
                props.put("CACHE_SIZE", String.valueOf(c_size));
                props.put("CACHE_WAY", String.valueOf(1));
                props.put("CACHE_COMMENT", "Hybrid "+h+" cache,size: "+c_size+" : "+comment);
                props.store(new FileOutputStream("config.properties"), "store");
                Process proc = Runtime.getRuntime().exec("java -jar Cache_Simulator.jar");
                SimulatorRunner.copy(proc.getInputStream(), System.out);
                SimulatorRunner.copy(proc.getErrorStream(), System.out);
                proc.waitFor();
                if(proc.exitValue() == 1){
                    throw new ExportException("Process fail exception.");
                }
            }
        }
    }
    
    public void resumeExperiment(String path) throws IOException, InterruptedException{
        SimulatorResumer resumer =  new SimulatorResumer(path);
        String name;
        while((name = resumer.resumeNext()) != null){
                System.out.println(name);
                Process proc = Runtime.getRuntime().exec("java -jar Cache_Simulator.jar "+path+"/"+name);
                SimulatorRunner.copy(proc.getInputStream(), System.out);
                SimulatorRunner.copy(proc.getErrorStream(), System.out);
                proc.waitFor();
        }
    }
}
