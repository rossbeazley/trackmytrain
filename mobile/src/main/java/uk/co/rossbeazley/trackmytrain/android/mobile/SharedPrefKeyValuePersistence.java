package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.content.Context;
import android.content.SharedPreferences;

import uk.co.rossbeazley.trackmytrain.android.KeyValuePersistence;

public class SharedPrefKeyValuePersistence implements KeyValuePersistence {

    private final SharedPreferences sharedPreferences;

    public SharedPrefKeyValuePersistence(Context application) {
        sharedPreferences = application.getSharedPreferences("trackmytrain", Context.MODE_PRIVATE);
    }

    @Override
    public void put(String key, String value) {
        sharedPreferences.edit().putString(key,value).commit();
    }

    @Override
    public String get(String key) {
        return sharedPreferences.getString(key,null);
    }
}
