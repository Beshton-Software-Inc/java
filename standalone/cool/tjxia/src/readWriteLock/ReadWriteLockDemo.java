package readWriteLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        ReadWriteLockedList readWriteLockedList = new ReadWriteLockedList();
        for (int i = 1; i <= 5; i++) {
            int temp = i;
            new Thread(() -> {
                readWriteLockedList.add(new String(temp + ""));
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {
            int temp = i;
            new Thread(() -> {
                readWriteLockedList.get(temp - 1);
            }, String.valueOf(i)).start();
        }
    }

    static class ReadWriteLockedList {
        private volatile List<Object> list = new ArrayList<>();
        private java.util.concurrent.locks.ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public void add(Object o){
            readWriteLock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " add");
                list.add(o);
                System.out.println(Thread.currentThread().getName() + " added");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }


        public void get(int i){
            readWriteLock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " get");
                list.get(i);
                System.out.println(Thread.currentThread().getName() + " got");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readWriteLock.readLock().lock();
            }
        }
    }
}
