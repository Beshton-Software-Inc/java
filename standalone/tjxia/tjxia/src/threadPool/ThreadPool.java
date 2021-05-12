package threadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService parkingLot = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10 ; i++) {
            parkingLot.execute(new Park());
        }

        parkingLot.shutdown();
    }
}

class Park implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getName() + " park");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " leave");
    }
}
