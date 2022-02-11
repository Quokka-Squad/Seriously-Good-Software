package ch6;

import ch1.Container;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {
    private Container a, b;

    @BeforeEach
    void beforeEach() {
        a = new Container();
        b = new Container();
    }

    @Test
    void testNewContainerIsEmpty() {
        String message = "new container is not empty";

        assertTrue(a.getAmount() == 0, message);
        assertEquals(a.getAmount(), 0, 0, message);
        assertThat(message, a.getAmount(), closeTo(0, 0));
    }
}
