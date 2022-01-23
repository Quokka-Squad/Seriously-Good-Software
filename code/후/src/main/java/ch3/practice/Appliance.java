package ch3.practice;

public class Appliance {

    private int powerConsumption;
    private boolean isOn;
    private Grid connectedGrid;

    public Appliance(int powerConsumption) {
        this.powerConsumption = powerConsumption;
        this.isOn = false;
    }

    public void plugInto(Grid grid) {
        if (this.connectedGrid == null) {
            this.connectedGrid = grid;
            return;
        }
        if (this.connectedGrid != null && isOn) {
            this.connectedGrid.setResidualPower(-powerConsumption);
        }
        this.connectedGrid = grid;
    }

    public void on() {
        this.isOn = true;
        this.connectedGrid.setResidualPower(-powerConsumption);
    }

    public void off() {
        this.isOn = false;
        this.connectedGrid.setResidualPower(powerConsumption);
    }
}
