import java.util.*;

public class Container {

    private Group group = new Group(this);

    private static class Group {
        double amountPerContainer;
        Set<Container> members;

        Group(Container c) {
            members = new HashSet<>();
            members.add(c);
        }
    }

    public double getAmount() {
        return group.amountPerContainer;
    }

    public void addWater(double amount) {
        double amountPerContainer = amount / group.members.size();
        group.amountPerContainer += amountPerContainer;
    }

    public void connectTo(Container other) {
        if (group == other.group) {
            return;
        }
        int size1 = group.members.size();
        int size2 = other.group.members.size();

        double tot1 = group.amountPerContainer * size1;
        double tot2 = other.group.amountPerContainer * size2;
        double newAmount = (tot1 + tot2) / (size1 + size2);

        group.members.addAll(other.group.members);
        group.amountPerContainer = newAmount;
        for (Container x: other.group.members) {
            x.group = group;
        }
    }

    public int groupSize() {
        return group.members.size();
    }

    public void flush() {
        group.amountPerContainer = 0;
    }
}
