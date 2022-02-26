package ch8.threads;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Container {

    private static final ReentrantLock globalLock = new ReentrantLock();
    private Group group = new Group(this);

    private static class Group {
        static final AtomicInteger nGroups = new AtomicInteger();
        double amountPerContainer;
        Set<Container> members = new HashSet<>();
        final int id = nGroups.incrementAndGet();

        private Group(Container container) {
            members.add(container);
        }
    }

    public double getAmount() {
        synchronized (group) {
            return group.amountPerContainer;
        }
    }

    public void addWater(double amount) {
        while (true) {
            Object moniter = group;
            synchronized (moniter) {
                if (moniter == group) {
                    double amountPerContainer = amount / group.members.size();
                    group.amountPerContainer += amountPerContainer;
                    return;
                }
            }
        }
    }

    public void connectTo(Container other) {
        globalLock.lock();
        synchronized (group) {
            synchronized (other.group) {
                globalLock.unlock();

                if (group == other.group) {
                    return;
                }

                int size1 = group.members.size();
                int size2 = other.group.members.size();
                double total1 = group.amountPerContainer * size1;
                double total2 = other.group.amountPerContainer * size2;

                group.amountPerContainer = (total1 + total2) / (size1 + size2);
                group.members.addAll(other.group.members);

                for (Container c : other.group.members) {
                    c.group = group;
                }
            }
        }
    }

    public void connectTo2(Container other) {
        if (group == other.group) {
            return;
        }

        Object firstMonitor, secondMonitor;
        if (group.id > other.group.id) {
            firstMonitor = other.group;
            secondMonitor = group;
        } else {
            firstMonitor = group;
            secondMonitor = other.group;
        }

        synchronized (firstMonitor) {
            synchronized (secondMonitor) {
                int size1 = group.members.size();
                int size2 = other.group.members.size();
                double total1 = group.amountPerContainer * size1;
                double total2 = other.group.amountPerContainer * size2;

                group.amountPerContainer = (total1 + total2) / (size1 + size2);
                group.members.addAll(other.group.members);

                for (Container c : other.group.members) {
                    c.group = group;
                }
            }
        }
    }

    public void connectTo3(Container other) {
        while (true) {
            if (group == other.group) {
                return;
            }
            Object firstMonitor = group;
            Object secondMonitor = other.group;
            if (group.id > other.group.id) {
                firstMonitor = other.group;
                secondMonitor = group;
            }
            synchronized (firstMonitor) {
                synchronized (secondMonitor) {
                    if ((firstMonitor == group && secondMonitor == other.group) ||
                            (secondMonitor == group && firstMonitor == other.group)) {
                        int size1 = group.members.size();
                        int size2 = other.group.members.size();
                        double total1 = group.amountPerContainer * size1;
                        double total2 = other.group.amountPerContainer * size2;

                        group.amountPerContainer = (total1 + total2) / (size1 + size2);
                        group.members.addAll(other.group.members);

                        for (Container c : other.group.members) {
                            c.group = group;
                        }
                        return;
                    }
                }
            }
        }
    }

    public int groupSize() {
        return group.members.size();
    }

    public void flush() {
        group.amountPerContainer = 0;
    }
}
