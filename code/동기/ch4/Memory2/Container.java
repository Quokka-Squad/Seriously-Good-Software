package code.동기.ch4.Memory2;

public class Container {

    private Container[] group;
    private float amount;

    public float getAmount() {  return amount; }

    public void connectTo(Container other) {

        if (group == null) {
            group = new Container[]{this};
        }
        if (other.group == null) {
            other.group = new Container[]{other};
        }
        if (group == other.group) return;

        int size1 = group.length,
                size2 = other.group.length;
        float amount1 = amount * size1,
                amount2 = other.amount * size2,
                newAmount = (amount1 + amount2) / (size1 + size2);

        Container[] newGroup = new Container[size1 + size2];

        int i = 0;
        for (Container a: group) {
            a.group = newGroup;
            a.amount = newAmount;
            newGroup[i++] = a;
        }
        for (Container b : other.group) {
            b.group = newGroup;
            b.amount = newAmount;
            newGroup[i++] = b;
        }
    }

    public void addWater(float amount) {
        if (group == null) {
            this.amount += amount;
        } else {
            float amountPerContainer = amount / group.length;
            for (Container c : group)
                c.amount += amountPerContainer;
        }
    }
}
