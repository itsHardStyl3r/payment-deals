package me.hardstyl3r.payments.managers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.Set;

public class FileManager<T> {
    private final JsonAdapter<Set<T>> adapter;

    public FileManager(Type type, Object jsonAdapter) {
        Moshi moshi = new Moshi.Builder().add(jsonAdapter).build();
        this.adapter = moshi.adapter(Types.newParameterizedType(Set.class, type));
    }

    public Set<T> getFromJson(String json) {
        try {
            return adapter.fromJson(json);
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            return null;
        }
    }
}
