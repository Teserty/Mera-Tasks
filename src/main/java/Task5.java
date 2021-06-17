public class Task5 {
    static class MyThread implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String [] args){
        new Thread(new MyThread()).start();
        System.out.println("Afs");
    }
}
