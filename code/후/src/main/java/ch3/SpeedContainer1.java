package ch3;

import java.util.*;

public class SpeedContainer1 {

    private Set<SpeedContainer1> group;
    private double amount;

    public SpeedContainer1() {
        group = new HashSet<>();
        group.add(this);
    }

    public double getAmount() {
        return amount;
    }

    public void connectTo(SpeedContainer1 other) {
        if (group == other.group) {
            return;
        }

        int size1 = group.size();
        int size2 = other.group.size();
        double total1 = amount * size1;
        double total2 = amount * size2;
        double newAmount = (total1 + total2) / (size1 + size2);

        group.addAll(other.group);
        for (SpeedContainer1 c : other.group) {
            c.group = group;
        }
        for (SpeedContainer1 c : group) {
            c.amount = newAmount;
        }
    }

    public void addWater(double amount) {
        double amountPerContainer = amount / group.size();
        for (SpeedContainer1 c : group) {
            c.amount += amountPerContainer;
        }
    }
}
