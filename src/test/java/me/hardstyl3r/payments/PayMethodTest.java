package me.hardstyl3r.payments;

import me.hardstyl3r.payments.objects.PayMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayMethodTest {

    @Test
    public void testPayMethodGetter() {
        PayMethod payMethod1 = new PayMethod("PUNKTY", 15, 100.0);
        assertEquals("PUNKTY", payMethod1.getId());
        assertEquals(15, payMethod1.getDiscount());
        assertEquals(100.0, payMethod1.getLimit());

        PayMethod payMethod2 = new PayMethod("mZysk", 10, 180.0);
        assertEquals("mZysk", payMethod2.getId());
        assertEquals(10, payMethod2.getDiscount());
        assertEquals(180.0, payMethod2.getLimit());
    }
}
