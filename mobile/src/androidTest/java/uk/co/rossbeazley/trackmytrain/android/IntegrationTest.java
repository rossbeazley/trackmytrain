package uk.co.rossbeazley.trackmytrain.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.departures.Stations;
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
    private DepartureQuery initialDepQuery;


    public void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        ComponentName component = Departures.componentName();
        intent.setComponent(component);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        myDepartureQueryCommands = new MyDepartureQueryCommands();
        TrackMyTrainApp.departureQueryCommands = myDepartureQueryCommands;

        myCanPresentDepartureQueries = new MyCanPresentDepartureQueries(myDepartureQueryCommands);
        TrackMyTrainApp.departuresPresenter = myCanPresentDepartureQueries;


        activity = getInstrumentation().startActivitySync(intent);


        Station crl = Stations.fromString("CRL");
        Direction bon = Direction.to(Stations.fromString("BON"));
        initialDepQuery = new DepartureQuery(crl, bon);

        Runnable runnable = new Runnable() {
            public void run() {
                for (DeparturesQueryView view : myCanPresentDepartureQueries.departureQueryViews) {
                    view.present(new DeparturesQueryViewModel(initialDepQuery));
                }
            }
        };

        InstrumentationRegistry.getInstrumentation().runOnMainSync(runnable);

    }


    public void tearDown() {
        activity.finish();
    }

    public void test_viewWillDispatchQueryBackToPresenter() {

        performDeparturesQuery();

        assertThat(myDepartureQueryCommands.query, is(initialDepQuery));
    }

    public void performDeparturesQuery() {
        final View viewById = activity.findViewById(R.id.getdepartures);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                viewById.performClick();

            }
        });
    }

    public void test_viewWillDispatchQueryWithDifferentFromStation() {

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                ((AutoCompleteTextView) activity.findViewById(R.id.at)).setText("BOL");
                ((TextView) activity.findViewById(R.id.to)).setText("CHORLEY");
            }
        });

        performDeparturesQuery();

        Station crl = Stations.fromString("CRL");
        Station bolton = Stations.fromString("BON");
        final DepartureQuery expectedQuery = new DepartureQuery(bolton, Direction.to(crl));
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
