package ch8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockExample2 {
    private static final ReentrantLock globalLock = new ReentrantLock();
    private Object monitor = new Object();

    public void a(DeadLockExample2 other) {
//        globalLock.lock();
        synchronized (this) {
            synchronized (other) {
//                globalLock.unlock();
                for (int i = 0; i < 3000; i++) {
                    System.out.println(Thread.currentThread().getName().charAt(7) + "\t" + i);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLockExample2 employee1 = new DeadLockExample2();
        DeadLockExample2 employee2 = new DeadLockExample2();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            threads.add(new Thread(() -> {
//                for (int j = 0; j < 10000; j++) {
                    employee1.a(employee2);
//                }
            }));
        }

        for (int i = 0; i < 3; i++) {
            threads.add(new Thread(() -> {
//                for (int j = 0; j < 10000; j++) {
                    employee2.a(employee1);
//                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
