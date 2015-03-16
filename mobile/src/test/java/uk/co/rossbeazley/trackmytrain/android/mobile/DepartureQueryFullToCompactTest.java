package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.view.View;
import android.widget.TextView;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class DepartureQueryFullToCompactTest {


    @Test @Ignore("not working with robolectric")
    public void atFieldIsHiddenOnQuery() {

        TestTrackMyTrainApp.instance.departures(Station.fromString("CRL"), TestDataBuilder.anyDirection());
        final Departures activity = activity();
        activity.findViewById(R.id.getdepartures).performClick();

        int viewVisibility = activity.findViewById(R.id.at).getVisibility();

        assertThat(viewVisibility, is(View.GONE));
    }


    @Test @Ignore("WIP")
    public void toFieldIsHiddenOnQuery() {

        TestTrackMyTrainApp.instance.departures(Station.fromString("CRL"), TestDataBuilder.anyDirection());
        final Departures activity = activity();
        activity.findViewById(R.id.getdepartures).performClick();

        int viewVisibility = activity.findViewById(R.id.to).getVisibility();

        assertThat(viewVisibility, is(View.GONE));
    }



    private Departures activity() {
        ActivityController<Departures> actCtl = Robolectric.buildActivity(Departures.class);
        return actCtl.create()
                .start()
                .resume()
                .visible()
                .get();
    }

}
