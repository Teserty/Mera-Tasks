import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.Stream;


public class Main {
    //private static ExecutorService executor= Executors.newSingleThreadExecutor();
    public static void main(String [] args) {
        Task1 task1 = new Task1();
        task1.runNotepad();
    }
    //public static Future<Double> sum(Double a, Double b){
    //    return executor.submit(()->{
    //        Thread.sleep(100);
    //        return a + b;
    //    });
    //}
}
