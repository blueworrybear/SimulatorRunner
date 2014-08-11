/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulatorrunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bear
 */
public class CacheProperty {
    
    public final static int DEFAULT = 0;
    public final static int HYBRID = 1;
    public final static int HYBRID_RESULT = 2;
    public final static int LOCAL_PARSER = 3;
    
    public final static int LOAD_PORT = 1;
    public final static int LOAD_ALL = 2;
    
    //SWITCH_TYPE
    public final static int SWITCH_SIMPLE = 0;
    public final static int SWITCH_TCP = 1;
    
    public static Properties props;
    public final static int level = Integer.valueOf(getConfig("CACHE_LEVEL"));
    public static int size = Integer.valueOf(getConfig("CACHE_SIZE"));
    public static int ways = Integer.valueOf(getConfig("CACHE_WAY"));
    public static int policy = Integer.valueOf(getConfig("CACHE_POLICY"));
    public static String comment = getConfig("CACHE_COMMENT");
    public final static boolean is_direct = Integer.valueOf(getConfig("CACHE_DIRECT")) == 1;
    public final static boolean is_central = Integer.valueOf(getConfig("CACHE_CENTRAL")) == 1;
    public static int switch_type = Integer.valueOf(getConfig("SWITCH_TYPE"));
    
    public static void storeProperties(){
        try {
            props.store(new FileOutputStream("config.properties"), "store");
        } catch (IOException ex) {
            Logger.getLogger(CacheProperty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void setProperties(String key,String value){
        if (props == null) {
            loadProperties();
        }
        props.put(key, value);
    }
    
    private static void loadProperties() {
         props = new Properties();
         try {
              props.load(new FileInputStream("config.properties"));
         } catch (FileNotFoundException e) {
         } catch (IOException e) {
         }
    }

    private static String getConfig(String key) {
        if (props == null) {
            loadProperties();
        }
        return props.getProperty(key);
    }
}
