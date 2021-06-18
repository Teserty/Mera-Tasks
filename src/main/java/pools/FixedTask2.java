package main.java.pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedTask2 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 1000; i++) {
            Runnable task;
            if(i%2==0)
                task = new TaskA();
            else
                task = new TaskB();
            long added = System.nanoTime();
            service.submit(() -> {
                long started = System.nanoTime();
                System.out.println(started - added);
                task.run();
                System.out.println(System.nanoTime() - started);
            });
        }
    }
    static class TaskA implements Runnable{
        @Override
        public void run() {
            System.out.println("a");
        }
    }
    static class TaskB implements Runnable{
        @Override
        public void run() {
            System.out.println("b");
        }
    }
}

