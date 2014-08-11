/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulatorrunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import static simulatorrunner.CacheProperty.props;

/**
 *
 * @author Jeffery
 */
public class SimulatorRunner {

    public static void copy(InputStream in, OutputStream out) throws IOException {
    while (true) {
      int c = in.read();
      if (c == -1) break;
      out.write((char)c);
    }
  }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        props.put("PARSER", String.valueOf(0));
        props.put("LOAD_TABLE", String.valueOf(0));
        props.put("HYBRID_RESULT", String.valueOf(0));
        props.store(new FileOutputStream("config.properties"), "store");

        if (args.length < 1) {
            System.out.println("Please specify argument.");
            System.exit(0);
        }
        String experiment = args[0];
        ExperimentRunner runner = new ExperimentRunner();
        if(experiment.equals("resume")){
            System.out.println("Resume experiment in "+args[1]);
            String path = args[1];
            runner.resumeExperiment(path);
        }
        
        if(experiment.equals("size")){
            System.out.println("Start cache size experiment.");
            runner.sizeVsHitrate(args[1]);
        }
        
         if(experiment.equals("size_tcp")){
            System.out.println("Start cache size_tcp experiment.");
            runner.sizeVsHitrateTCP(args[1]);
        }
         
        if(experiment.equals("size_per")){
            System.out.println("Start cache size_tcp experiment.");
            runner.sizeVsHitratePerport(args[1]);
        }
        
        if(experiment.equals("way")){
            System.out.println("Start cache ways experiment.");
            runner.wayVsHitrate(CacheProperty.size, args[1]);
        }
        if(experiment.equals("hybrid")){
            System.out.println("Start hybrid experiment.");
            runner.hybrid(args[1]);
        }
        
        if(experiment.equals("hybrid_result")){
            System.out.println("Start hybrid result experiment.");
            runner.hybrid_result(args[1]);
        }
    } 

    private static void copy(PrintStream err, PrintStream out) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
