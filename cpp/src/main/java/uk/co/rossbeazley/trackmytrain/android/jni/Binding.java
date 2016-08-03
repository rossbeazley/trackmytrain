package uk.co.rossbeazley.trackmytrain.android.jni;

public class Binding {

    static {
        System.loadLibrary("cpp");
    }

    public native String stringFromJNI();
}
