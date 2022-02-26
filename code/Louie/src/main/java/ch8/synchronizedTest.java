package ch8;

public class synchronizedTest {
    private int n;

    public void increment(Member member) throws InterruptedException {
        n++;
        member.up();
    }

    public static void main(String[] args) throws InterruptedException {
        synchronizedTest test = new synchronizedTest();
        Member member = new Member();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    try {
                        test.increment(member);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(test.n + "\t" + member.id);
    }

    static class Member {
        private int id;

        public synchronized void up() throws InterruptedException {
            id++;
        }
    }
}
