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
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class ServiceTest {

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
    public void startsTrackingAService() {
        TrainViewModel train = new TrainViewModel(new Train("2", "10:00", "09:00", "1"));
        CapturingServiceView csv = new CapturingServiceView();

        TrackMyTrainApp.instance.attach(csv);
        ((TextView)act.findViewById(R.id.selectedservice)).setText("2");
        act.findViewById(R.id.trackbutton).performClick();

        assertThat(csv.trackedTrain,is(train));
    }

    @Test
    public void trackedServiceOnScreen() {

        ((TextView)act.findViewById(R.id.selectedservice)).setText("2");
        act.findViewById(R.id.trackbutton).performClick();

        TrainViewModel expectedTrain = new TrainViewModel(new Train("2", "10:00", "09:00", "1"));
        TestTrackMyTrainApp.instance.watch("2");

        String trackedText = String.valueOf(((TextView)act.findViewById(R.id.trackedservice)).getText());
        assertThat(trackedText,is(expectedTrain.toString()));
    }


    @Test
    public void trackedServiceOnScreenUpdates() {

        ((TextView)act.findViewById(R.id.selectedservice)).setText("2");
        act.findViewById(R.id.trackbutton).performClick();

        TestTrackMyTrainApp.trackedService = new Train("2", "On Time", "09:00", "1");
        TestTrackMyTrainApp.instance.watch("2");

        Train expectedTrain = new Train("2", "10:00", "09:00", "1");
        TestTrackMyTrainApp.trackedService = expectedTrain;
        TestTrackMyTrainApp.instance.watch("2");

        String trackedText = String.valueOf(((TextView)act.findViewById(R.id.trackedservice)).getText());
        assertThat(trackedText,is(expectedTrain.toString()));
    }



    @Test
    public void stoppingTrackingClears() {
        ((TextView)act.findViewById(R.id.selectedservice)).setText("2");
        act.findViewById(R.id.trackbutton).performClick();

        TestTrackMyTrainApp.instance.watch("2");

        act.findViewById(R.id.stopbutton).performClick();

        String trackedText = String.valueOf(((TextView)act.findViewById(R.id.trackedservice)).getText());
        assertThat(trackedText,is(""));
    }



    static class CapturingServiceView implements ServiceView {

        public TrainViewModel trackedTrain;

        @Override
        public void present(TrainViewModel train) {
            trackedTrain=train;
        }

        @Override
        public void hide() {

        }
    }
}