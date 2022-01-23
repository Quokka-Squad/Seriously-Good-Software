package chap03;

import java.util.Objects;

public class Appliance {
    private int energy;
    private boolean power;
    private Grid connectedGrid;

    public Appliance(int useEnergy) {
        this.energy = useEnergy;
        this.power = false;
    }

    public void plugIn(Grid grid) {
        connectedGrid = grid;
    }

    public void on() throws Exception {
        if (connectedGrid.getMaxEnergy() < energy) {
            throw new Exception("전력 부족");
        }
        connectedGrid.useEnergy(energy);
        power = true;
    }

    public void off() {
        power = false;
        connectedGrid.useEnergy(power * ( -1));
    }
}
