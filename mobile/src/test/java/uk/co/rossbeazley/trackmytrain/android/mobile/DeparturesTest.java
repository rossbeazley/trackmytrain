package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.mobile.Departures;

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

}