package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.ActivityToHouseTesting;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class RendersDepartureListTest {

    @Before
    public void failingSetup() {
        try {
            activity();
        } catch (Exception e) {

        }

    }

    ActivityToHouseTesting activity() {
        ActivityToHouseTesting activity = ActivityToHouseTesting.create();
        activity.setContentView(R.layout.servicedetails);
        return activity;
    }

    @Test
    public void showsTheLoadingSpinner() {
        ActivityToHouseTesting activity = activity();

        ListViewDeparturesView resultsView = new ListViewDeparturesView(activity);

        resultsView.loading();

        View spinner = activity.findViewById(R.id.departurelist_loading);
        assertThat(spinner.getVisibility(),is(View.VISIBLE));
    }

    @Test
    public void hidesTheListWhenLoading() {
        ActivityToHouseTesting activity = activity();

        ListViewDeparturesView resultsView = new ListViewDeparturesView(activity);

        resultsView.loading();

        View list = activity.findViewById(R.id.departurelist);
        assertThat(list.getVisibility(),is(View.GONE));
    }

    @Test
    public void hidesTheLoadingSpinner() {
        ActivityToHouseTesting activity = activity();

        ListViewDeparturesView resultsView = new ListViewDeparturesView(activity);

        resultsView.loading();
        final DeparturesViewModel list = TrainViewModel.list(TestDataBuilder.anyTrains());
        resultsView.present(list);

        View spinner = activity.findViewById(R.id.departurelist_loading);
        assertThat(spinner.getVisibility(),is(View.GONE));
    }
}
