package uk.co.rossbeazley.trackmytrain.android;

import java.util.HashMap;
import java.util.Map;

public class HashMapKeyValuePersistence implements KeyValuePersistence {

    Map<String,String> kvStore = new HashMap<>();

    @Override
    public void put(String key, String value) {
        kvStore.put(key,value);
    }

    @Override
    public String get(String key) {
        return kvStore.get(key);
    }
}
