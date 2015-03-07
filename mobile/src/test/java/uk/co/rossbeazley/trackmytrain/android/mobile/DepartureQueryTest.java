package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class DepartureQueryTest {


    @Test
    public void theScreenLoadsWithLastQueriedFromStation() {

        TestTrackMyTrainApp.instance.departures(Station.fromString("BON"), TestDataBuilder.anyDirection());

        TextView viewById = (TextView) activity().findViewById(R.id.from);
        String fromText = String.valueOf(viewById.getText());
        assertThat(fromText,is("BON"));
    }

    @Test
    public void theScreenLoadsWithLastQueriedToStation() {

        TestTrackMyTrainApp.instance.departures(TestDataBuilder.anyStation(), Direction.to(Station.fromString("MON")));

        TextView viewById = (TextView) activity().findViewById(R.id.to);
        String toText = String.valueOf(viewById.getText());
        assertThat(toText,is("MON"));
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
