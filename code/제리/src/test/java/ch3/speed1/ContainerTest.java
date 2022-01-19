package ch3.speed1;

import static org.junit.jupiter.api.Assertions.*;

import ch1.Container;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContainerTest {

	@Test
	@DisplayName("Speed1 Inner static Class Group 따로 선언 테스트")
	void test() {
		Container a = new Container();
		Container b = new Container();
		Container c = new Container();
		Container d = new Container();

		a.addWater(12);
		assertEquals(12.0, a.getAmount());

		a.connectTo(b);
		assertEquals(6.0, a.getAmount());
		assertEquals(6.0, b.getAmount());

		d.addWater(8);

		b.connectTo(c);
		assertEquals(4.0, a.getAmount());
		assertEquals(4.0, b.getAmount());
		assertEquals(4.0, c.getAmount());

		b.connectTo(d);
		assertEquals(5.0, a.getAmount());
		assertEquals(5.0, b.getAmount());
		assertEquals(5.0, c.getAmount());
		assertEquals(5.0, d.getAmount());

		b.addWater(4);
		assertEquals(6.0, a.getAmount());
		assertEquals(6.0, b.getAmount());
		assertEquals(6.0, c.getAmount());
		assertEquals(6.0, d.getAmount());

	}

}