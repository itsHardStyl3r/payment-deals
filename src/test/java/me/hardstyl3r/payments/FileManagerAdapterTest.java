package me.hardstyl3r.payments;

import me.hardstyl3r.payments.adapters.OrderAdapter;
import me.hardstyl3r.payments.adapters.PayMethodAdapter;
import me.hardstyl3r.payments.managers.FileManager;
import me.hardstyl3r.payments.objects.Order;
import me.hardstyl3r.payments.objects.PayMethod;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("FieldCanBeLocal")
// This class is used to test the FileManager class along with Order and PayMethod adapters, so essentially
// it is a test for JSON parsing and object creation.
public class FileManagerAdapterTest {

    private final String pmJson = """
            [
              {
                "id": "PUNKTY",
                "discount": "15",
                "limit": "100.00"
              },
              {
                "id": "mZysk",
                "discount": "10",
                "limit": "180.00"
              },
              {
                "id": "BosBankrut",
                "discount": "5",
                "limit": "200.00"
              }
            ]
            """;

    private final String ordersJson = """
            [
              {
                "id": "ORDER1",
                "value": "100.00",
                "promotions": [
                  "mZysk"
                ]
              },
              {
                "id": "ORDER2",
                "value": "200.00",
                "promotions": [
                  "BosBankrut"
                ]
              },
              {
                "id": "ORDER3",
                "value": "150.00",
                "promotions": [
                  "mZysk",
                  "BosBankrut"
                ]
              },
              {
                "id": "ORDER4",
                "value": "50.00"
              }
            ]
            """;

    private final FileManager<Order> orderFileManager = new FileManager<>(Order.class, new OrderAdapter());
    private final FileManager<PayMethod> payMethodFileManager = new FileManager<>(PayMethod.class, new PayMethodAdapter());

    /**
     * Test for parsing JSON to Order objects.
     * Expecting Sets to be equals in such way that order does not matter.
     */
    @Test
    public void testGetOrdersFromJson() {
        Set<Order> expectedOrders = Set.of(
                new Order("ORDER2", 200.00, List.of("BosBankrut")),
                new Order("ORDER3", 150.00, List.of("mZysk", "BosBankrut")),
                new Order("ORDER1", 100.00, List.of("mZysk")),
                new Order("ORDER4", 50.00, null)
        );
        Set<Order> ordersFromJson = orderFileManager.getFromJson(ordersJson);

        assertEquals(expectedOrders, ordersFromJson);
    }

    /**
     * Test for parsing JSON to PayMethods objects.
     * Expecting Sets to be equals in such way that order does not matter.
     */
    @Test
    public void testGetPayMethodsFromJson() {
        Set<PayMethod> payMethods = Set.of(
                new PayMethod("PUNKTY", 15, 100.00),
                new PayMethod("mZysk", 10, 180.00),
                new PayMethod("BosBankrut", 5, 200.00)
        );
        Set<PayMethod> payMethodsFromJson = payMethodFileManager.getFromJson(pmJson);

        assertEquals(payMethods, payMethodsFromJson);
    }
}
