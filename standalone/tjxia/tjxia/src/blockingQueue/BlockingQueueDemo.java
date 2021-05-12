package blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> abq = new ArrayBlockingQueue<>(5);
        for (int i = 1; i <= 5; i++) {
            final String temp = "" + i;
            new Thread(()->{
                while(true){
                    if(abq.offer(temp)){
                        System.out.println("Push " + temp + " Success");
                    } else {
                        System.out.println("Try to push " + temp + " to Blocking Queue");
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                while(true){
                    String out = abq.poll();
                    if(out != null){
                        System.out.println("Get " + out + " from Blocking Queue");
                    } else {
                        System.out.println("Get fail");
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


    }
}
