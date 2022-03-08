package ch4.memory2;

import java.util.*;

public class Container {

    private Container[] group;
    private float amount;

    public float getAmount() {
        return amount;
    }

    public void connectTo(Container other) {
        if (group == null) {
            group = new Container[]{this};
        }
        if (other.group == null) {
            other.group = new Container[]{other};
        }
        if (group == other.group) {
            return;
        }

        int size1 = group.length;
        int size2 = other.group.length;
        float total1 = amount * size1;
        float total2 = other.amount * size2;
        float newAmount = (total1 + total2) / (size1 + size2);

        Container[] newGroup = new Container[size1 + size2];

        int i = 0;
        for (Container c : group) {
            c.group = newGroup;
            c.amount = newAmount;
            newGroup[i++] = c;
        }
        for (Container c : other.group) {
            c.group = newGroup;
            c.amount = newAmount;
            newGroup[i++] = c;
        }
    }

    public void addWater(float amount) {
        if (group == null) {
            this.amount += amount;
            return;
        }
        float amountPerContainer = amount / group.length;
        for (Container c : group) {
            c.amount += amountPerContainer;
        }
    }
}
