package chap03;

import java.util.HashSet;
import java.util.Set;

public class Grid {
    private int maxEnergy;
    private Set<Appliance> connections = new HashSet<>();


    public Grid(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setConnection(Appliance appliance) {
        connections.add(appliance);
    }

    public void deleteConnection(Appliance appliance) {
        connections.remove(appliance);
    }

    public void useEnergy(int energy) {
        maxEnergy -= energy;
    }

    public int residualPower() {
        return maxEnergy;
    }

}
