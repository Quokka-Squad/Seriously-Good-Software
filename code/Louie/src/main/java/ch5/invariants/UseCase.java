package ch5.invariants;

import ch5.contracts.Container;

public class UseCase {
    public static void main(String[] args) {
        Container a = new Container();
        Container b = new Container();
        Container c = new Container();
        Container d = new Container();

        a.addWater(12);
        a.connectTo(b);
        d.addWater(8);
        System.out.println(a.getAmount() + "\t\t" + b.getAmount() + "\t\t" + c.getAmount() + "\t\t" + d.getAmount());

        b.connectTo(c);
        System.out.println(a.getAmount() + "\t\t" + b.getAmount() + "\t\t" + c.getAmount() + "\t\t" + d.getAmount());

        b.connectTo(d);
        System.out.println(a.getAmount() + "\t\t" + b.getAmount() + "\t\t" + c.getAmount() + "\t\t" + d.getAmount());
    }
}