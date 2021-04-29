package messageQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class MessageQueue {
    Queue<Object> q = new LinkedList<>();

    public synchronized void produce(Object o){
        q.offer(o);
        System.out.println("Thread " + Thread.currentThread().getName() + " produce " + o.toString());
    }

    public synchronized void consume(){
        while(q.size() == 0){

        }
        Object o = q.poll();
        System.out.println("Thread " + Thread.currentThread().getName() + " consume " + o.toString());
    }

    public static void main(String[] args) {
        MessageQueue mq = new MessageQueue();
        Producer producer = new Producer(mq);
        Consumer consumer = new Consumer(mq);
        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++) {
                producer.produce(new String(i + ""));
            }
        }, "produce thread").start();

        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++) {
                consumer.consume();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "consume thread").start();
    }
}
