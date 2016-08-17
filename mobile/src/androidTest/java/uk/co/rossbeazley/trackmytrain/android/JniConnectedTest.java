package uk.co.rossbeazley.trackmytrain.android;


import uk.co.rossbeazley.trackmytrain.android.jni.Binding;

public class JniConnectedTest {

    @org.junit.Test
    public void hello() {
        System.out.printf(new Binding().stringFromJNI());
    }
}
