public class Task3 {
    static Thread1 thread1 = new Thread1();
    static Thread2 thread2 = new Thread2();
    static Object a= 0;
    public static void main(String [] args){
        thread1.start();
        thread2.start();
    }
    public static class Thread1 extends Thread{
        public void run(){
            System.out.println("Заснул");
            try {
                synchronized (thread1) {
                    thread1.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Работаю");
        }
    }
    public static class Thread2 extends Thread{
        public void run(){
            try {
                Thread.sleep(500);
                System.out.println("Просыпайся давай");
                synchronized (thread1) {
                    thread1.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
