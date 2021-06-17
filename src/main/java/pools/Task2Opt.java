package pools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class Task2Opt {
    public static void main(String [] args){
        MyThreadPool threadPool = new MyThreadPool(2);
        for (int i=0; i < 1000; i++){
            threadPool.execute(new TaskA());
            threadPool.execute(new TaskB());
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.isRunning = false;
    }
    static abstract class Task implements Runnable{
        abstract String getId();
    }
    static class TaskA extends Task{
        String id = "a";
        @Override
        public void run() {
            System.out.println("a");
        }

        @Override
        String getId() {
            return id;
        }
    }
    static class TaskB extends Task{
        String id = "b";
        @Override
        public void run() {
            System.out.println("a");
        }
        @Override
        String getId() {
            return id;
        }
    }
    public static class MyThreadPool implements Executor {
        private static class Storage{
            public Storage(Runnable runnable){
                this.runnable =runnable;
                added = System.nanoTime();
            }
            public long stared(){
                started = System.nanoTime();
                return started - added;
            }
            public long finished(){
                finished = System.nanoTime();
                return finished - started;
            }
            private Runnable runnable;
            private long added;
            private long started;
            private long finished;

            public Runnable getRunnable() {
                return runnable;
            }
        }
        private final HashMap<String,Queue<Storage>> workQueue = new HashMap<>();
        private volatile boolean isRunning = true;

        public MyThreadPool(int nThreads) {
            new Thread(new TaskWorker("a")).start();
            new Thread(new TaskWorker("b")).start();
            workQueue.put("a", new ConcurrentLinkedQueue<>());
            workQueue.put("b",new ConcurrentLinkedQueue<>());
        }

        @Override
        public void execute(Runnable command) {
            if (isRunning) {
                workQueue.get(((Task)command).getId()).offer(new Storage(command));
            }
        }

        public void shutdown() {
            isRunning = false;
        }

        private final class TaskWorker implements Runnable {
            String id;

            public TaskWorker(String a) {
                id = a;
            }

            @Override
            public void run() {
                while (isRunning) {
                    Storage nextTask = workQueue.get(id).poll();
                    if (nextTask != null) {
                        System.out.println(nextTask.stared());
                        nextTask.getRunnable().run();
                        System.out.println(nextTask.finished());
                    }
                }
            }
        }
    }
}
