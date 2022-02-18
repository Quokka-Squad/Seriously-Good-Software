package ch7.readable;

import java.util.HashSet;
import java.util.Set;

/**
 * A <code>Container</code>는 사실상 용량 제한이 없는 수조를 나타낸다.
 * <p>
 * 물은 넣거나 뺄 수 있다. 두 수조는 영구적인 파이프로 연결될 수 있다. 두 수조를 직/간접적으로 연결하면 두 수조는 상호작용을 주고받으며 물은 둘 사이에
 * <b>균등하게</b> 배분된다.
 * <p>
 * 이 수조에 연결된 모든 수조의 집합을 이 수조의
 * <i>group</i> 이라고 한다.
 *
 * @author Marco Faella
 * @version 1.0
 */
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
        for (Container c : group) {
            c.amount += amountPerContainer;
        }
    }
}
