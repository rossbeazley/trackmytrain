package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.widget.TextView;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import uk.co.rossbeazley.trackmytrain.android.R;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class RemembersDepartureQueryTest {


    @Test @Ignore("WIP, WTF, plan to migrate other DepartureQueryTest into this test where we poke view directly")
    public void theScreenLoadsWithLastQueriedFromStation() {
        ActivityToHouseTesting activity = activity();
        activity.setContentView(R.layout.servicedetails);

        TextView viewById = (TextView) activity.findViewById(R.id.at);



        String fromText = String.valueOf(viewById.getText());
        assertThat(fromText,is("BON"));
    }


    private ActivityToHouseTesting activity() {
        ActivityController<ActivityToHouseTesting> actCtl = Robolectric.buildActivity(ActivityToHouseTesting.class);
        return actCtl.create()
                .start()
                .resume()
                .visible()
                .get();
    }

}
