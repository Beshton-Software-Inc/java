package countingSemaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CountingSemaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            final int temp = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("Thread " + Thread.currentThread().getName() + " acquire semaphore.");
                    TimeUnit.SECONDS.sleep(temp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Thread " + Thread.currentThread().getName() + " release semaphore.");
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
