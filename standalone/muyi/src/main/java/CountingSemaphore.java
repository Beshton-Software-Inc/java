import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class CountingSemaphore {

    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(1);
        int maxThreads = 100;
        for (int i = 0; i < maxThreads; i++) {
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName() + " is Running" + " Available permits: " + semaphore.availablePermits());
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    finally {
                        semaphore.release();
                        System.out.println(Thread.currentThread().getName() + " finish" + " Available permits: " + semaphore.availablePermits() + "-----------------");
                    }
                }
            }).start();

        }
    }

}
