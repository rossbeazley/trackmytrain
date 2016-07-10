package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Test;

import fakes.CapturingServiceView;
import fakes.CapturingTrackedServiceListener;
import uk.co.rossbeazley.trackmytrain.android.trackedService.TrackedServicePresenter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TrackingAServiceTest {

    private String serviceId;
    private String scheduledTime;
    private String estimatedTime;

    private String platform;
    private TrackedServicePresenter trackedServicePresenter;
    private TrainViewModel expectedTrainViewModel;

    private CapturingServiceView serviceView;
    private CapturingCanTrackService tmt;
    private Train expectedTrain;

    @Before
    public void setUp() throws Exception {
        serviceId = "3Olk7M389Qp5JIdkXAQt4g==";
        scheduledTime = "20:48";
        estimatedTime = "On time";
        platform = "2";
        expectedTrain = new Train(serviceId, estimatedTime, scheduledTime, platform, false);
        expectedTrainViewModel = new TrainViewModel(expectedTrain);

        serviceView = new CapturingServiceView();

        tmt = new CapturingCanTrackService();
        trackedServicePresenter = new TrackedServicePresenter(tmt);

        trackedServicePresenter.attach(serviceView);
    }

    @Test
    public void theOneWhereWeSelectAServiceAndTrackingStarts() {
        trackedServicePresenter.watch(serviceId);
        tmt.updateServiceData(expectedTrain);
        assertThat(serviceView.serviceDisplayed, is(expectedTrainViewModel));
    }

    @Test
    public void theOneWhereAnnounceTrackingStarted() {
        trackedServicePresenter.watch(serviceId);
        assertThat(serviceView.trackingIs, is(CapturingServiceView.STARTED));
    }

    @Test
    public void theOneWhereWeAreUpdatedAboutTheSelectedService() {
        trackedServicePresenter.watch(serviceId);
        tmt.updateServiceData(expectedTrain);
        serviceView.serviceDisplayed=null;
        final Train train = new Train(serviceId, "20:52", scheduledTime, platform, false);
        final TrainViewModel expectedTrain = new TrainViewModel(train);

        tmt.updateServiceData(train);
        assertThat(serviceView.serviceDisplayed, is(expectedTrain));
    }

    @Test
    public void theOneWhereTheServiceViewIsHiddenWhenWeStopTracking() {
        trackedServicePresenter.watch(serviceId);
        tmt.updateServiceData(expectedTrain);
        serviceView.serviceDisplayed=null;
        trackedServicePresenter.unwatch();
        assertThat(serviceView.visibility, is(serviceView.HIDDEN));
    }


    public static class CapturingCanTrackService implements CanTrackService {
        private TrackedServiceListener trackedServiceListener = new CapturingTrackedServiceListener();
        private boolean tracking;

        @Override
        public void addTrackedServiceListener(TrackedServiceListener trackedServiceListener) {

            this.trackedServiceListener = trackedServiceListener;
        }

        @Override
        public void watchService(String serviceId) {
            this.tracking = true;
            trackedServiceListener.trackingStarted();
        }

        @Override
        public boolean isTracking() {
            return tracking;
        }

        @Override
        public void unwatchService() {
            this.tracking = false;
            trackedServiceListener.trackingStopped();
        }

        public void updateServiceData(Train train) {
            trackedServiceListener.trackedServiceUpdated(train);
        }

    }
}
