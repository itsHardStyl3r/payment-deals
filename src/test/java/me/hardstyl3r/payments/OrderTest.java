package me.hardstyl3r.payments;

import me.hardstyl3r.payments.objects.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    public void testOrderGetter() {
        Order order1 = new Order("ORDER1", 100.0, List.of("mZysk"));
        assertEquals("ORDER1", order1.getId());
        assertEquals(100.0, order1.getValue());
        assertEquals(List.of("mZysk"), order1.getPromotions());

        Order order2 = new Order("ORDER4", 50, null);
        assertEquals("ORDER4", order2.getId());
        assertEquals(50, order2.getValue());
        assertNull(order2.getPromotions());
    }

    @SuppressWarnings({"EqualsWithItself", "SimplifiableAssertion", "EqualsBetweenInconvertibleTypes", "ConstantValue"})
    @Test
    public void testOrderEquals() {
        Order order1 = new Order("ORDER1", 100.0, List.of("mZysk"));
        Order order2 = new Order("ORDER1", 100.0, List.of("mZysk"));

        assertEquals(order1, order1);
        assertEquals(order1, order2);
        assertNotSame(order1, order2);
        assertFalse(order1.equals(""));
        assertFalse(order2.equals(null));
    }
}
