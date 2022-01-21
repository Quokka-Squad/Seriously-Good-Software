package ch3;

public class SpeedContainer3 {

    private double amount;
    private SpeedContainer3 parent = this;
    private int size = 1;

    private SpeedContainer3 findRootAndCompress() {
        if (parent != this) {
            parent = parent.findRootAndCompress();
        }
        return parent;
    }

    public double getAmount() {
        SpeedContainer3 root = findRootAndCompress();
        return root.amount;
    }

    public void addWater(double amount) {
        SpeedContainer3 root = findRootAndCompress();
        if (root.amount + amount < 0) {
            throw new IllegalArgumentException("수조에 물이 충분히 들어있지 않습니다.");
        }
        root.amount += amount / root.size;
    }
}
