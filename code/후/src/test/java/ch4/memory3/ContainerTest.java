package ch4.memory3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContainerTest {

    @Test
    void runExampleScenario() {
        int a = Container.newContainer();
        int b = Container.newContainer();
        int c = Container.newContainer();
        int d = Container.newContainer();

        Container.addWater(a, 12);
        Container.addWater(d, 8);
        Container.connect(a, b);
        assertEquals(6.0, Container.getAmount(a));
        assertEquals(6.0, Container.getAmount(b));
        assertEquals(0.0, Container.getAmount(c));
        assertEquals(8.0, Container.getAmount(d));

        Container.connect(b, c);
        assertEquals(4.0, Container.getAmount(a));
        assertEquals(4.0, Container.getAmount(b));
        assertEquals(4.0, Container.getAmount(c));
        assertEquals(8.0, Container.getAmount(d));

        Container.connect(b, d);
        assertEquals(5.0, Container.getAmount(a));
        assertEquals(5.0, Container.getAmount(b));
        assertEquals(5.0, Container.getAmount(c));
        assertEquals(5.0, Container.getAmount(d));
    }
}
