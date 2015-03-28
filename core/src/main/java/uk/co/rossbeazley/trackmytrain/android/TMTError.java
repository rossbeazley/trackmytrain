package uk.co.rossbeazley.trackmytrain.android;

public class TMTError {
    private final String error;

    public TMTError(String error) {
        this.error = error;
    }

    public String toString() {
        return error;
    }

    public boolean equals(Object object) {
        return object.toString().equals(toString());
    }
}
