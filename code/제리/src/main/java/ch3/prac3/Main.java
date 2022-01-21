package ch3.prac3;

public class Main {

	public static void main(String[] args) {
		Appliance tv = new Appliance(150);
		Appliance radio = new Appliance(30);
		Grid grid = new Grid(3000);

		tv.plugInto(grid);
		radio.plugInto(grid);

		System.out.println(grid.residualPower());

		tv.on();

		System.out.println(grid.residualPower());

		radio.on();
		System.out.println(grid.residualPower());

		Grid grid1 = new Grid(140);

		tv.plugInto(grid1);

		System.out.println(grid.residualPower());

		System.out.println(grid1.residualPower());

		tv.on();

		System.out.println(grid.residualPower());

		System.out.println(grid1.residualPower());

	}

}
