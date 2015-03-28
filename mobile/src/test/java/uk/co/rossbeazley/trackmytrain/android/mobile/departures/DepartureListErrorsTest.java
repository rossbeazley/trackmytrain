package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.ActivityToHouseTesting;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class DepartureListErrorsTest {

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

    @Test @Ignore("wip")
    public void hidesTheLoadingSpinner() {
        ActivityToHouseTesting activity = activity();

        ListViewDeparturesView resultsView = new ListViewDeparturesView(activity);

        resultsView.loading();

        resultsView.error(new TMTError("Its an error"));

        View spinner = activity.findViewById(R.id.departurelist_loading);
        assertThat(spinner.getVisibility(),is(View.GONE));
    }

    @Test @Ignore("wip")
    public void toastsTheError() {
        ActivityToHouseTesting activity = activity();

        ListViewDeparturesView resultsView = new ListViewDeparturesView(activity);

        resultsView.loading();

        final TMTError error = new TMTError("Its an error");
        resultsView.error(error);


        String toastText = ShadowToast.getTextOfLatestToast();
        assertThat(toastText, is(error.toString()));
    }

}
