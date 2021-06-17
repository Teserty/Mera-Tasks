public class Deadlock {
    static Object a = new Object();
    static Object b = new Object();
    static class Thread1 extends Thread{
        public void run(){
            synchronized (a){
                try {
                    Thread.sleep(500);
                    synchronized (b){
                        System.out.println(a);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    static class Thread2 extends Thread{
        public void run(){
            synchronized (b){
                try {
                    Thread.sleep(500);
                    synchronized (a){
                        System.out.println(a);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();
    }
}