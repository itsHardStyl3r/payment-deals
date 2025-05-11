package me.hardstyl3r.payments;

import me.hardstyl3r.payments.adapters.OrderAdapter;
import me.hardstyl3r.payments.adapters.PayMethodAdapter;
import me.hardstyl3r.payments.managers.FileManager;
import me.hardstyl3r.payments.managers.ShopManager;
import me.hardstyl3r.payments.objects.Order;
import me.hardstyl3r.payments.objects.PayMethod;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Correct usage: java -jar <jar-file> <orders-file> <payment-methods-file>.");
            return;
        }
        FileManager<Order> orderManager = new FileManager<>(Order.class, new OrderAdapter());
        FileManager<PayMethod> payMethodManager = new FileManager<>(PayMethod.class, new PayMethodAdapter());
        String ordersJson = "", payMethodsJson = "";
        try {
            ordersJson = Files.readString(Paths.get(args[0]));
        } catch (Exception e) {
            System.out.println("Error reading orders file: " + e.getMessage() + ".");
        }
        try {
            payMethodsJson = Files.readString(Paths.get(args[1]));
        } catch (Exception e) {
            System.out.println("Error reading payment methods file: " + e.getMessage() + ".");
        }

        Set<Order> orders = orderManager.getFromJson(ordersJson);
        Set<PayMethod> payMethods = payMethodManager.getFromJson(payMethodsJson);

        ShopManager shopManager = new ShopManager(payMethods);
        Map<String, Double> receipts = shopManager.finalizeOrder(orders);
        receipts.forEach((key, value) -> {
            System.out.println(key + " " + value);
        });
    }
}