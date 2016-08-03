import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.jni.Binding;

public class Jnitest {

    @Test
    public void hello() {
        System.out.printf(new Binding().stringFromJNI());
    }
}
