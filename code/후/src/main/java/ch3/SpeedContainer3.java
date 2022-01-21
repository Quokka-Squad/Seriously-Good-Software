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
}
