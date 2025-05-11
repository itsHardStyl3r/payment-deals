package me.hardstyl3r.payments.managers;

import me.hardstyl3r.payments.objects.Order;
import me.hardstyl3r.payments.objects.PayMethod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShopManager {

    private final Set<PayMethod> paymentMethods;
    // PUNKTY is a special payment method and the name could change.
    private final String PUNKTY = "PUNKTY";

    public ShopManager(Set<PayMethod> payMethods) {
        this.paymentMethods = payMethods;
    }

    public Set<PayMethod> getPayMethods() {
        return new HashSet<>(paymentMethods);
    }

    public Map<String, Double> finalizeOrder(Set<Order> orders) {
        Map<String, Double> receipts = new HashMap<>();
        Map<String, Double> availableFunds = new HashMap<>();
        for (PayMethod pm : getPayMethods()) {
            availableFunds.put(pm.getId(), pm.getLimit());
        }
        PayMethod punkty = null;
        for (PayMethod pm : getPayMethods()) {
            if (pm.getId().equals(PUNKTY)) {
                punkty = pm;
                break;
            }
        }

        for (Order order : orders) {
            double orderValue = order.getValue();
            double remaining = orderValue;
            // So, the algorithm should prefer to pay with points first.
            // We'll see if we can pay with them.
            if (punkty != null && availableFunds.get(PUNKTY) > 0) {
                double punktyLimit = availableFunds.get(PUNKTY);
                // This will determine whether we can pay with points for the whole order.
                if (punktyLimit >= orderValue) {
                    double discount = orderValue * punkty.getDiscount() / 100.0;
                    double toPay = orderValue - discount;
                    availableFunds.put(PUNKTY, punktyLimit - toPay);
                    if (receipts.containsKey(PUNKTY)) {
                        receipts.put(PUNKTY, receipts.get(PUNKTY) + toPay);
                    } else {
                        receipts.put(PUNKTY, toPay);
                    }
                    continue; // The order has been completed.
                }
                // At this point, we determined we can't pay the full price with points,
                // so, according to the third rule, we apply 10% for the whole order.
                if (punktyLimit >= (0.1 * orderValue)) {
                    double discountedTotal = orderValue * 0.9;
                    double toUse = Math.min(discountedTotal, punktyLimit);
                    availableFunds.put(PUNKTY, punktyLimit - toUse);
                    if (receipts.containsKey(PUNKTY)) {
                        receipts.put(PUNKTY, receipts.get(PUNKTY) + toUse);
                    } else {
                        receipts.put(PUNKTY, toUse);
                    }
                    remaining = discountedTotal - toUse;
                }
            }

            // Let's determine the best card payment for the remaining amount.
            // We're looking for the lowest price, so use MAX_VALUE.
            PayMethod best = null;
            double bestPrice = Double.MAX_VALUE;

            for (PayMethod method : getPayMethods()) {
                if (method.getId().equals(PUNKTY)) continue; // Skipping points payment, naturally.
                if (order.getPromotions() != null && !order.getPromotions().contains(method.getId())) continue;
                double discounted = remaining * (1 - method.getDiscount() / 100.0);
                double balance = availableFunds.get(method.getId());
                // Let's make sure if we can afford to pay with this method.
                // If so, we'll check if it's the best price.
                if (balance >= discounted && discounted < bestPrice) {
                    best = method;
                    bestPrice = discounted;
                }
            }
            // At this point, we've determined the best way to pay.
            // If we didn't, we would use any available method that's left.
            if (best != null) {
                double left = availableFunds.get(best.getId());
                availableFunds.put(best.getId(), left - bestPrice);
                if (receipts.containsKey(best.getId())) {
                    receipts.put(best.getId(), receipts.get(best.getId()) + bestPrice);
                } else {
                    receipts.put(best.getId(), bestPrice);
                }
            } else {
                for (PayMethod method : getPayMethods()) {
                    if (method.getId().equals(PUNKTY)) continue;
                    double available = availableFunds.get(method.getId());
                    if (available >= remaining) {
                        availableFunds.put(method.getId(), available - remaining);
                        if (receipts.containsKey(method.getId())) {
                            receipts.put(method.getId(), receipts.get(method.getId()) + remaining);
                        } else {
                            receipts.put(method.getId(), remaining);
                        }
                        break;
                    }
                }
            }
        }
        return receipts;
    }
}
