package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.app.Activity;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.Arrays;

import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryViewModel;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class AutocompleteDeparturesTest {


    @Test @Ignore("totally stuffed, cant get robolectric to play ball")
    public void selectedSecondItemIsUsedInTheQuery() {

        final TestingAct activity = activity();
        activity.setContentView(R.layout.servicedetails);

        AndroidDeparturesQueryView view = new AndroidDeparturesQueryView(activity);
        final DeparturesQueryViewModel departuresQueryViewModel = new DeparturesQueryViewModel(null, TestDataBuilder.anyDirection(), Arrays.asList(new Station("First", "ONE"), new Station("Second", "TWO")));

        view.present(departuresQueryViewModel);


        AutoCompleteTextView actv = (AutoCompleteTextView) activity.findViewById(R.id.to);

        final ListView parent = new ListView(actv.getContext());
        parent.setAdapter(actv.getAdapter());

        actv.getOnItemClickListener().onItemClick(parent,actv,1,1);

        assertThat(departuresQueryViewModel.getAt(),is(new Station("Second", "TWO")));
    }

    private TestingAct activity() {
        ActivityController<TestingAct> actCtl = Robolectric.buildActivity(TestingAct.class);
        return actCtl.create()
                .start()
                .resume()
                .visible()
                .get();
    }

    public static class TestingAct extends Activity implements FindsView {

    }


    /**
     *
     * when the screen loads, the to and from and displayed from persistence
     *
     * --when you select an item from the drop down, that station is sent in on the query--
     * -- cant do this one
     *
     *
     */
}
