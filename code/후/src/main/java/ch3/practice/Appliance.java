package ch3.practice;

public class Appliance {

    private int powerConsumption;
    private boolean status;
    private Grid grid;

    public Appliance(int powerConsumption) {
        this.powerConsumption = powerConsumption;
        this.status = false;
    }

    public void plugInto(Grid grid) {
        if (this.grid == null) {
            this.grid = grid;
            return;
        }
        if (this.grid != null && status) {
            this.grid.setResidualPower(-powerConsumption);
        }
        this.grid = grid;
    }

    public void on() {
        this.status = true;
        this.grid.setResidualPower(-powerConsumption);
    }

    public void off() {
        this.status = false;
        this.grid.setResidualPower(powerConsumption);
    }
}
