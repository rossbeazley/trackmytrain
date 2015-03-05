package uk.co.rossbeazley.trackmytrain.android;

public interface KeyValuePersistence {
    void put(String key, String value);

    String get(String key);
}
