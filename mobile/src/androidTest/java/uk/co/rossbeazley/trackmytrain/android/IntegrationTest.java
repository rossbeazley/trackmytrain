package uk.co.rossbeazley.trackmytrain.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.test.ActivityUnitTestCase;
import android.test.InstrumentationTestCase;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.CanPresentDepartureQueries;
import uk.co.rossbeazley.trackmytrain.android.DepartureQueryCommands;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.mobile.Departures;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class IntegrationTest extends InstrumentationTestCase {

    private MyDepartureQueryCommands myDepartureQueryCommands;
    private MyCanPresentDepartureQueries myCanPresentDepartureQueries;
    private Activity activity;


    public void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        ComponentName component = new ComponentName(BuildConfig.APPLICATION_ID,BuildConfig.APPLICATION_ID+".mobile.Departures");
        intent.setComponent(component);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        myDepartureQueryCommands = new MyDepartureQueryCommands();
        TrackMyTrainApp.departureQueryCommands = myDepartureQueryCommands;

        myCanPresentDepartureQueries = new MyCanPresentDepartureQueries(myDepartureQueryCommands);
        TrackMyTrainApp.departuresPresenter = myCanPresentDepartureQueries;


        activity = getInstrumentation().startActivitySync(intent);

    }


    public void tearDown() {
        activity.finish();
    }

    public void test_viewWillDispatchQueryBackToPresenter() {

        assertThat("Number of attached views", myCanPresentDepartureQueries.departureQueryViews.size() > 0, is(true));

        Station crl = Station.fromString("CRL");
        Direction bon = Direction.to(Station.fromString("BON"));
        DepartureQuery dep = new DepartureQuery(crl, bon);

        for (DeparturesQueryView view : myCanPresentDepartureQueries.departureQueryViews) {
            view.present(new DeparturesQueryViewModel(dep));
        }

        final View viewById = activity.findViewById(R.id.getdepartures);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                viewById.performClick();

            }
        });

        assertThat(myDepartureQueryCommands.query, is(dep));
    }

    @Test
    public void test_viewWillDispatchQueryWithDifferentFromStation() {

        Station crl = Station.fromString("CRL");
        Station bolton = Station.fromString("BON");
        Direction toBolton = Direction.to(bolton);
        final DepartureQuery dep = new DepartureQuery(crl, toBolton);
        final DepartureQuery expectedQuery = new DepartureQuery(bolton, Direction.to(crl));

        final AutoCompleteTextView trainAtStationField = (AutoCompleteTextView) activity.findViewById(R.id.at);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {

                for (DeparturesQueryView view : myCanPresentDepartureQueries.departureQueryViews) {
                    view.present(new DeparturesQueryViewModel(dep));
                }

                trainAtStationField.setText("BOL");
                ((TextView) activity.findViewById(R.id.to)).setText("CHORLEY");

                activity.findViewById(R.id.getdepartures).performClick();

            }
        });

        assertThat(myDepartureQueryCommands.query, is(expectedQuery));
    }

    private static class MyDepartureQueryCommands implements DepartureQueryCommands {


        private DepartureQuery query;

        @Override
        public void departures(DepartureQuery query) {
            this.query = query;
        }
    }

    private static class MyCanPresentDepartureQueries implements CanPresentDepartureQueries {
        private List<DeparturesView> departureViews;
        private List<DeparturesQueryView> departureQueryViews;
        private MyDepartureQueryCommands myDepartureQueryCommands;

        private MyCanPresentDepartureQueries(MyDepartureQueryCommands myDepartureQueryCommands) {
            this.myDepartureQueryCommands = myDepartureQueryCommands;
            departureViews = new ArrayList<>();
            departureQueryViews = new ArrayList<>();
        }

        @Override
        public void attach(DeparturesView departureView) {
            this.departureViews.add(departureView);
        }

        @Override
        public void detach(DeparturesView departuresView) {
            this.departureViews.remove(departuresView);
        }

        @Override
        public void attach(DeparturesQueryView departuresQueryView) {
            departureQueryViews.add(departuresQueryView);
            departuresQueryView.attach(myDepartureQueryCommands);
        }

        @Override
        public void detach(DeparturesQueryView departuresQueryView) {
            departureQueryViews.remove(departuresQueryView);
        }
    }
}
