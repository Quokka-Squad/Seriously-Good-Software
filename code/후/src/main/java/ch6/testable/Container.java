package ch6.testable;

import java.util.HashSet;
import java.util.Set;

public class Container {

    private Set<Container> group;
    private double amount;

    public Container() {
        group = new HashSet<>();
        group.add(this);
    }

    public double getAmount() {
        return amount;
    }

    public boolean connectTo(Container other) {
        if (group == other.group) {
            return false;
        }

        int size1 = group.size(),
            size2 = other.group.size();
        double total1 = amount * size1,
            total2 = amount * size2,
            newAmount = (total1 + total2) / (size1 + size2);

        group.addAll(other.group);
        for (Container c : other.group) {
            c.group = group;
        }
        for (Container c : group) {
            c.amount = newAmount;
        }
        return true;
    }

    public void addWater(double amount) {
        double amountPerContainer = amount / group.size();
        for (Container c : group) {
            c.amount += amountPerContainer;
        }
    }
}
