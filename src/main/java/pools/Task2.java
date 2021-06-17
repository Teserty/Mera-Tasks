package pools;

import javafx.concurrent.Task;
import sun.nio.ch.ThreadPool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class Task2 {
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
    static class TaskA implements Runnable{
        @Override
        public void run() {
            System.out.println("a");
        }
    }
    static class TaskB implements Runnable{
        @Override
        public void run() {
            System.out.println("a");
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
        private final Queue<Storage> workQueue = new ConcurrentLinkedQueue<>();
        private volatile boolean isRunning = true;

        public MyThreadPool(int nThreads) {
            for (int i = 0; i < nThreads; i++) {
                new Thread(new TaskWorker()).start();
            }
        }

        @Override
        public void execute(Runnable command) {
            if (isRunning) {
                workQueue.offer(new Storage(command));
            }
        }

        public void shutdown() {
            isRunning = false;
        }

        private final class TaskWorker implements Runnable {

            @Override
            public void run() {
                while (isRunning) {
                    Storage nextTask = workQueue.poll();
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
