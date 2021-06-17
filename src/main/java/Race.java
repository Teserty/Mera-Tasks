public class Race {
    static volatile Integer x = 0;
    static class MyThread extends Thread{
        public void run(){
            for ( int i = 0; i < 100000; i++ ) {
                x++;
            }
        }
    }
    static class SafeMyThread extends Thread{
        public void run(){
            for ( int i = 0; i < 100000; i++ ) {
                synchronized(x) {
                    x++;
                }
            }
        }
    }
    public static void main(String [] args){
        for (int i = 0; i < 10; i++){
            new MyThread().start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(x);
    }
}
