package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class DeparturesTest {

    public Departures act;

    @Before
    public void buildActivity() {
        ActivityController<Departures> actCtl = Robolectric.buildActivity(Departures.class);
        act = actCtl.create()
                .start()
                .resume()
                .visible()
                .get();
    }

    @Test
    public void displaysTheDepartureResultsForAJourney() {
        final TrainViewModel train = new TrainViewModel(new Train("1", "", "", ""));
        final TrainViewModel train1 = new TrainViewModel( new Train("2", "", "", ""));

        act.findViewById(R.id.getdepartures).performClick();

        final ListView departureList = (ListView) act.findViewById(R.id.departurelist);

        ListAdapter adapter = departureList.getAdapter();
        TrainViewModel viewedTrain1 = (TrainViewModel) adapter.getItem(0);
        TrainViewModel viewedTrain2 = (TrainViewModel) adapter.getItem(1);

        assertThat(viewedTrain1,is(train));
        assertThat(viewedTrain2,is(train1));
    }

    @Test @Ignore("More failing robolectric")
    public void selectsTrainIdFromList() {
        act.findViewById(R.id.getdepartures).performClick();
        final ListView listView = (ListView) act.findViewById(R.id.departurelist);
        Robolectric.shadowOf(listView).performItemClick(1);

        View trackingButton = listView.findViewWithTag("2");
        assertThat(trackingButton.getVisibility(),is(View.VISIBLE));
    }

    /**
     * introduce tests for list adapter directly. exercise from view with a view model?
     */
}