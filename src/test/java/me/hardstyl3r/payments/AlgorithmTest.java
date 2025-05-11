package me.hardstyl3r.payments;

import me.hardstyl3r.payments.adapters.OrderAdapter;
import me.hardstyl3r.payments.adapters.PayMethodAdapter;
import me.hardstyl3r.payments.managers.FileManager;
import me.hardstyl3r.payments.managers.ShopManager;
import me.hardstyl3r.payments.objects.Order;
import me.hardstyl3r.payments.objects.PayMethod;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class keeps a different copy of pmJson and ordersJson,
// so that other cases can be tested while keeping the other tests intact.
public class AlgorithmTest {

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

    // Copy the code from Main.
    @Test
    public void testAlgorithm() {
        Set<Order> orders = orderFileManager.getFromJson(ordersJson);
        Set<PayMethod> payMethods = payMethodFileManager.getFromJson(pmJson);
        ShopManager shopManager = new ShopManager(payMethods);

        Map<String, Double> receipts = shopManager.finalizeOrder(orders);
        Map<String, Double> expected = Map.of(
                "PUNKTY", 100.0,
                "BosBankrut", 190.0,
                "mZysk", 153.0
        );
        assertEquals(expected, receipts);
    }
}
