package ch5.contracts;

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

    public void connectTo(Container other) {
        if (group == other.group) {
            return;
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
    }

    public void addWater(double amount) {
        double amountPerContainer = amount / group.size();
        // 1. 사전 조건 검사
        if (this.amount + amountPerContainer < 0) {
            throw new IllegalArgumentException(
                "Not enough water to match the addWater request."
            );
        }
        // 2. 사후 조건 검사를 위한 데이터 저장
        double oldTotal = 0;
        assert (oldTotal = groupAmount()) >= 0;
        // 3. 실제 작업 수행
        for (Container c : group) {
            c.amount += amountPerContainer;
        }
        // 4. 사후 조건 검사
        assert postAddWater(oldTotal, amount) :
            "addWater failed its post condition!";
    }

    private double groupAmount() {
        double total = 0;
        for (Container c : group) {
            total += c.amount;
        }
        return total;
    }

    private boolean postAddWater(double oldTotal, double addedAmount) {
        return isGroupBalanced() && almostEqual(groupAmount(), oldTotal + addedAmount);
    }

    private boolean isGroupBalanced() {
        for (Container c : group) {
            if (c.amount != amount) {
                return false;
            }
        }
        return true;
    }

    private static boolean almostEqual(double x, double y) {
        final double EPSILON = 1E-4;
        return Math.abs(x - y) < EPSILON;
    }
}
