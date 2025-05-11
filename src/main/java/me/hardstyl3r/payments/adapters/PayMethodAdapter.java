package me.hardstyl3r.payments.adapters;

import com.squareup.moshi.FromJson;
import me.hardstyl3r.payments.objects.PayMethod;

public class PayMethodAdapter {
    @FromJson
    PayMethod orderFromJson(PayMethodJson json) {
        return new PayMethod(json.id, json.discount, json.limit);
    }
}
