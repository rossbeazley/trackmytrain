package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

public class DeparturesScreen {

    private ListViewDeparturesView departureView;
    private AndroidDeparturesQueryView departuresQueryView;

    public DeparturesScreen(final FindsView findsView, InputMethodManager inputMethodManager) {
        departureView = new ListViewDeparturesView(findsView);
        departuresQueryView = new AndroidDeparturesQueryView(findsView, inputMethodManager);
        TrackMyTrainApp.instance.attach(departureView);
        TrackMyTrainApp.instance.attach(departuresQueryView);
        TrackMyTrainApp.instance.attach(new NRELogoView(findsView));
        TrackMyTrainApp.instance.attach(new TrackedServiceView(findsView));
    }

    public void tearDown() {
        TrackMyTrainApp.instance.detach(departureView);
        TrackMyTrainApp.instance.detach(departuresQueryView);
        departureView=null;
        departuresQueryView=null;
    }

    private static class TrackedServiceView implements ServiceView {

        private final FindsView findsView;
        View container;
        TextView scheduledTime;
        TextView expectedTime;
        TextView platorm;
        Button stopTracking;

        public TrackedServiceView(FindsView findsView) {
            this.findsView = findsView;
            container = findsView.findViewById(R.id.trackedService);
            scheduledTime = (TextView) container.findViewById(R.id.scheduledtime);
            expectedTime = (TextView) container.findViewById(R.id.estimatedtime);
            platorm = (TextView) container.findViewById(R.id.platform);
            stopTracking = (Button) container.findViewById(R.id.trackbutton);
        }

        @Override
        public void present(final TrainViewModel train) {
            container.post(new Runnable() {
                @Override
                public void run() {
                    container.setVisibility(View.VISIBLE);
                    scheduledTime.setText(train.scheduledTime());
                    expectedTime.setText(train.estimatedTime());
                    platorm.setText(train.platform());
                    stopTracking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TrackMyTrainApp.instance.unwatch();
                        }
                    });
                }
            });
        }

        @Override
        public void hide() {
            container.post(new Runnable() {
                @Override
                public void run() {
                    container.setVisibility(View.GONE);
                }
            });
        }

        @Override
        public void trackingStarted() {
            container.post(new Runnable() {
                @Override
                public void run() {
                    container.setVisibility(View.VISIBLE);
                }
            });

        }
    }

    private static class NRELogoView implements DeparturesView {

        private final FindsView findsView;
        private View nreLogo;

        public NRELogoView(FindsView findsView) {
            this.findsView = findsView;
            nreLogo = findsView.findViewById(R.id.powered_by_nre);
        }

        @Override
        public void present(DeparturesViewModel trains) {

        }

        @Override
        public void loading() {
            final ViewGroup viewById = (ViewGroup) (findsView.findViewById(R.id.servicedetails));
            viewById.removeView(nreLogo);
            viewById.removeView(findsView.findViewById(R.id.powered_by_nre_spacer));
            TrackMyTrainApp.instance.detach(this);
        }

        @Override
        public void error(TMTError error) {

        }

    }
}
