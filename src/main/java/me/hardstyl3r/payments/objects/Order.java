package me.hardstyl3r.payments.objects;

import java.util.List;

public class Order {
    private final String id;
    private final double value;
    private final List<String> promotions;

    public Order(String id, double value, List<String> promotions) {
        this.id = id;
        this.value = value;
        this.promotions = promotions;
    }

    public String getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public List<String> getPromotions() {
        return promotions;
    }
}
