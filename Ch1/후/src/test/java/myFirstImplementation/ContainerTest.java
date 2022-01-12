package myFirstImplementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContainerTest {

    @Test
    void runExampleScenario() {
        Container a = new Container();
        Container b = new Container();
        Container c = new Container();
        Container d = new Container();

        a.addWater(12);
        d.addWater(8);
        a.connectTo(b);
        assertEquals("6.0 6.0 0.0 8.0",
            a.getAmount() + " " + b.getAmount() + " " + c.getAmount() + " " + d.getAmount());

        b.connectTo(c);
        assertEquals("4.0 4.0 4.0 8.0",
            a.getAmount() + " " + b.getAmount() + " " + c.getAmount() + " " + d.getAmount());

        b.connectTo(d);
        assertEquals("5.0 5.0 5.0 5.0",
            a.getAmount() + " " + b.getAmount() + " " + c.getAmount() + " " + d.getAmount());
    }
}
