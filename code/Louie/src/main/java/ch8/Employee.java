package ch8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Employee{
    private static final ReentrantLock globalLock = new ReentrantLock();
    private Object monitor = new Object();

    public static synchronized void classLevel() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName().charAt(7));
        }
    }

    public void instanceLevel() {
        synchronized (this) {
            for (int i = 0; i < 10000; i++) {
                System.out.println(Thread.currentThread().getName().charAt(7));
            }
        }
    }

    public void methodLevel() {
        synchronized (monitor) {
            for (int i = 0; i < 10000; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 10; j++) {
                    sb.append(Thread.currentThread().getName().charAt(7));
                }
                System.out.println(sb);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Employee employee = new Employee();
        List<Thread> threads = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            threads.add(new Thread(() -> new Employee().classLevel()));
//        }
//        for (int i = 0; i < 5; i++) {
//            threads.add(new Thread(() -> new Employee().instanceLevel()));
//        }
        for (int i = 0; i < 5; i++) {
            threads.add(new Thread(() -> employee.instanceLevel()));
        }
//        for (int i = 0; i < 3; i++) {
//            threads.add(new Thread(() -> employee.methodLevel()));
//        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
