package ch8.exercises;

import java.util.ArrayList;
import java.util.List;

public class AtomicPair<S, T> {
    private S first;
    private T second;

    public void setBoth(S first, T second) {
        this.first = first;
        this.second = second;
    }

    public S getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public static void main(String[] args) throws InterruptedException {
//        AtomicPair<Integer, Integer> atomicPair = new AtomicPair<>();
//        List<Thread> threads = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            threads.add(new Thread(){
//                @Override
//                public synchronized void run() {
//                for (int j = 1; j < 10000; j++) {
//                    atomicPair.setBoth(j, j * 10);
//                    Integer first = atomicPair.getFirst();
//                    Integer second = atomicPair.getSecond();
//                    if ((double) first / second != 0.1) {
//                        System.out.println(first + "\t" + second + "\t" + Thread.currentThread().getName().charAt(7));
//                    }
//                }
//            };
//        }
//
//        for (Thread thread : threads) {
//            thread.start();
//        }
//
//        for (Thread thread : threads) {
//            thread.join();
//        }
//
//        System.out.println("END");
    }
}
