package ch3.speed1;

import java.util.HashSet;
import java.util.Set;

/** A water container, optimized for speed of getAmount and addWater.
 *
 *  @author Marco Faella
 *  @version 1.0
 */
public class Container {

    private Group group = new Group(this);

    public double getAmount() { return group.amountPerContainer; }

    public void addWater(double amount) {
        double amountPerContainer = amount / group.members.size();
        group.amountPerContainer += amountPerContainer;
    }

    public void connectTo(Container other) {

        if (group==other.group) return;

        int size1 =       group.members.size(),
            size2 = other.group.members.size();
        double tot1 =       group.amountPerContainer * size1,
               tot2 = other.group.amountPerContainer * size2,
               newAmount = (tot1 + tot2) / (size1 + size2);
        
        group.members.addAll(other.group.members);
        group.amountPerContainer = newAmount;
        for (Container x: other.group.members) x.group = group;
    }

    public int groupSize() {
        return group.members.size();
    }

    public void flush() {
        group.amountPerContainer = 0;
    }
}

