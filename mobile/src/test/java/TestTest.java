import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.co.rossbeazley.trackmytrain.android.Departures;
import uk.co.rossbeazley.trackmytrain.android.R;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="mobile/src/main/AndroidManifest.xml", emulateSdk = 18)
public class TestTest {

    @Test
    public void findsViewInActivity() {

        Departures act = Robolectric.buildActivity(Departures.class)
                .create()
                .start()
                .resume()
                .visible()
                .get();

        View view = act.findViewById(R.id.serviceid);
        assertThat(view,is(not(nullValue())));
    }



}
