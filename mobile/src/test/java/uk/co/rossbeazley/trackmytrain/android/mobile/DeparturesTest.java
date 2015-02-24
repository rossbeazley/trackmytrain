package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.view.View;
import android.widget.Button;
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

import java.util.Arrays;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.mobile.Departures;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
    public void theScreenLoadsWithDefaultFromStation() {
        TextView viewById = (TextView) act.findViewById(R.id.from);
        String fromText = String.valueOf(viewById.getText());
        assertThat(fromText,is("SLD"));
    }

    @Test
    public void theScreenLoadsWithDefaultToStation() {
        TextView viewById = (TextView) act.findViewById(R.id.to);
        String toText = String.valueOf(viewById.getText());
        assertThat(toText,is("CRL"));
    }

    @Test @Ignore("WIP")
    public void displaysTheDepartureResultsForAJourney() {
        final Train train = new Train("1", "", "", "");
        final Train train1 = new Train("2", "", "", "");
        List<Train> expected = Arrays.asList(train, train1);

        act.findViewById(R.id.getdepartures).performClick();

        final ListView departureList = (ListView) act.findViewById(R.id.departurelist);

        //List<Train> trains = departureList.getAdapter().;



        //assertThat(trains,hasItems(train,train1));
    }

}