package uk.co.rossbeazley.trackmytrain.android.mobile;

public interface Analytics {
    void timing(long millis, String category, String variable);

    void event(String category, String label);
}
