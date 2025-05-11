package me.hardstyl3r.payments.adapters;

import com.squareup.moshi.FromJson;
import me.hardstyl3r.payments.objects.Order;

public class OrderAdapter {
    @FromJson
    Order orderFromJson(OrderJson json) {
        return new Order(json.id, json.value, json.promotions);
    }
}
