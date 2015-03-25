package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.co.rossbeazley.trackmytrain.android.R;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class RendersDepartureListTest {


    @Test
    public void showsTheLoadingSpinner() {
        ActivityToHouseTesting activity = ActivityToHouseTesting.create();
        activity.setContentView(R.layout.servicedetails);

        TextView viewById = (TextView) activity.findViewById(R.id.at);



        String fromText = String.valueOf(viewById.getText());
        assertThat(fromText,is("BON"));
    }


}
