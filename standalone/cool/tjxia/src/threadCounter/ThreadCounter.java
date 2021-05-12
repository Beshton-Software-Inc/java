package threadCounter;

import java.util.concurrent.TimeUnit;

public class ThreadCounter {
    int count = 0;
    public void increment(){
        synchronized (ThreadCounter.class) {
            count++;
            System.out.println(count + " being calc by Thread " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        ThreadCounter threadCounter = new ThreadCounter();
        new Thread(()->{
            while (true){
                threadCounter.increment();
            }
        }, "1").start();
        new Thread(()->{
            while (true){
                threadCounter.increment();
            }
        }, "2").start();
        new Thread(()->{
            while (true){
                threadCounter.increment();
            }
        }, "3").start();
    }
}
