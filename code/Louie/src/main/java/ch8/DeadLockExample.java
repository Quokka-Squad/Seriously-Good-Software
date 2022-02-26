package ch8;

import java.util.ArrayList;
import java.util.List;

public class DeadLockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    int n1;
    int n2;

    public void a() {
        synchronized (lock1) {
            synchronized (lock2) {
                System.out.println("n1 = " + ++n1);
            }
        }
    }

    public void b() {
        synchronized (lock2) {
            synchronized (lock1) {
                System.out.println("n2 = " + ++n2);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("START");

        DeadLockExample test = new DeadLockExample();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    test.a();
                }
            }));
        }

        for (int i = 0; i < 1; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    test.b();
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("END");
    }
}
