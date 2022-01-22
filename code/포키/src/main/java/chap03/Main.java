package chap03;

public class Main {

    public static void main(String[] args) throws Exception{
        Appliance tv = new Appliance(150);
        Appliance radio = new Appliance(30);
        Grid grid = new Grid(3000);

        tv.plugIn(grid);
        radio.plugIn(grid);

        System.out.println(grid.residualPower());
        tv.on();
        System.out.println(grid.residualPower());
        radio.on();
        System.out.println(grid.residualPower());
    }

}
