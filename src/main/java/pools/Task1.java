package pools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class Task1 {
    public static void main(String [] args){
        double[] a = new double[1000];
        for (int i = 0; i<1000; i++){
            a[i]=2000-i;
        }
        Job job = new Job(a);
        forkJoinPool.execute(job);
        for (int i = 0; i<1000; i++){
            System.out.println(job.arr[i]);
        }

    }
    static ForkJoinPool forkJoinPool = new ForkJoinPool(10);
    public static class Job extends RecursiveAction{
        private double[] arr;

        public Job(double[] arr) {
            this.arr = arr;
        }

        @Override
        protected void compute() {
            if (arr.length > 1){
                int mid = arr.length/2;
                double[] left = Arrays.copyOfRange(arr, 0, mid);
                double[] right = Arrays.copyOfRange(arr, mid, arr.length);
                Job job1 = new Job(left);
                Job job2 = new Job(right);
                invokeAll(job1, job2);
                merge(left, right);
            }
        }
        private void merge(double[] left, double[] right) {
            int i = 0,j = 0,m = 0;
            while (i < left.length && j < right.length) {
                if(left[i] <= right[j]){
                    arr[m] = left[i];
                    i++;
                }else{
                    arr[m] = right[j];
                    j++;
                }
                m++;
            }
            while (i < left.length) {
                arr[m] = left[i];
                i++;m++;
            }
            while (j < right.length) {
                arr[m] = right[j];
                j++;m++;
            }
        }
    }
}
