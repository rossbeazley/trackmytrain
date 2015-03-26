package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.mobile.ActivityToHouseTesting;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class RendersDepartureListTest {

    @Before
    public void failingSetup() {
        try {
            ActivityToHouseTesting activity = ActivityToHouseTesting.create();
        } catch (Exception e) {

        }

    }

    @Test
    public void showsTheLoadingSpinner() {
        ActivityToHouseTesting activity = ActivityToHouseTesting.create();
        activity.setContentView(R.layout.servicedetails);

        ListViewDeparturesView resultsView = new ListViewDeparturesView(activity);

        resultsView.loading();

        View spinner = activity.findViewById(R.id.departurelist_loading);
        assertThat(spinner.getVisibility(),is(View.VISIBLE));
    }

    @Test
    public void hidesTheListWhenLoading() {
        ActivityToHouseTesting activity = ActivityToHouseTesting.create();
        activity.setContentView(R.layout.servicedetails);

        ListViewDeparturesView resultsView = new ListViewDeparturesView(activity);

        resultsView.loading();

        View list = activity.findViewById(R.id.departurelist);
        assertThat(list.getVisibility(),is(View.GONE));
    }

}
