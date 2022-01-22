package chap03;

import java.util.HashSet;
import java.util.Set;

public class Grid {
    private int maxEnergy;



    public Grid(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void useEnergy(int energy) {
        maxEnergy -= energy;
    }

    public int residualPower() {
        return maxEnergy;
    }

}
