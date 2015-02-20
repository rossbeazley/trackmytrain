import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.ServiceDetails;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="mobile/src/main/AndroidManifest.xml", emulateSdk = 18)
public class TestTest {

    @Test
    public void findsViewInActivity() {

        ServiceDetails act = Robolectric.buildActivity(ServiceDetails.class)
                .create()
                .start()
                .resume()
                .visible()
                .get();

        View view = act.findViewById(R.id.serviceid);
        assertThat(view,is(not(nullValue())));
    }



}
