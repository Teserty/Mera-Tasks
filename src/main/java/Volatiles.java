public class Volatiles {
    static Long a = null;
    static volatile Long b = null;
    public static void main(String [] args){
        t1();
        t2();
    }
    public static void t1(){
        for (int i = 0; i < 10; i++){
            new Thread1().start();
        }
    }
    public static void t2(){
        for (int i = 0; i < 10; i++){
            new Thread2().start();
        }
    }
    public static class Thread1 extends Thread{
        public void run(){
            if(a == null){
                a = this.getId();
                System.out.println("a is now "+a);
            }
            System.out.println("a = "+a);
        }
    }
    public static class Thread2 extends Thread{
        public void run(){
            if(b == null){
                b = this.getId();
                System.out.println("b is now "+b);
            }
            System.out.println("b = "+b);
        }
    }
}
