public class Task4 {
    static Object a = 0;
    static Object b = 0;
    static synchronized void smethod(){
        System.out.println("synchronized method " + a);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static void method(){
        System.out.println("synchronized block");
        synchronized(b){
            System.out.println(b);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static class Thread1 extends Thread{
        public void run(){
            smethod();
        }
    }
    public static class Thread2 extends Thread{
        public void run(){
            method();
        }
    }
    public static void main(String [] args){
        for (int i = 0; i < 10; i++){
            new Thread1().start();
        }
        for (int i = 0; i < 10; i++){
            new Thread2().start();
        }
    }
}
