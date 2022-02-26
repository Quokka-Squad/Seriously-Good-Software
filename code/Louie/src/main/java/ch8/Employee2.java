package ch8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Employee2 {
    private static final ReentrantLock lock1 = new ReentrantLock();
    private static final ReentrantLock lock2 = new ReentrantLock();

    public synchronized void classLevel1() {
        lock1.lock();
        for (int i = 0; i < 10000; i++) {
//            System.out.println("CLASS11111");
            System.out.println(Thread.currentThread().getName().charAt(7));
        }
        lock1.unlock();
    }

    public synchronized void classLevel2() {
        lock2.lock();
        for (int i = 0; i < 10000; i++) {
//            System.out.println("CLASS22222");
            System.out.println(Thread.currentThread().getName().charAt(7));
        }
        lock2.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        Employee2 employee = new Employee2();
        List<Thread> threads = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            threads.add(new Thread(() -> employee.methodLevel()));
//        }
        for (int i = 0; i < 6; i++) {
            threads.add(new Thread(() -> new Employee2().classLevel1()));
        }
//        for (int i = 0; i < 1; i++) {
//            threads.add(new Thread(() -> employee.classLevel2()));
//        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
