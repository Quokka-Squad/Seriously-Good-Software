package ch8.exercises;

public class Exercise1 extends Thread {
    private static int[] array = new int[10000]; // 초기 값 지정

    public void run() {
            for (int i = 0; i < array.length; i++) {
                synchronized (this) {
                    array[i]++;
                }
            }
    }

    public static void main(String[] args) throws InterruptedException {
        Exercise1 exercise1 = new Exercise1();
        Exercise1 exercise2 = new Exercise1();
        for (int i = 0; i < 3; i++) {
            exercise1.run();
        }
        for (int i = 0; i < 3; i++) {
            exercise2.run();
        }

        exercise1.join();
        exercise2.join();

        for (int i : array) {
            if (i != 6) {
                throw new RuntimeException(i + "은 6이 아닙니다.");
            }
        }
    }
}
