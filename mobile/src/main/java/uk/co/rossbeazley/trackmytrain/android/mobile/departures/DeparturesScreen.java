package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
        TrackMyTrainApp.instance.attach(new DeparturesView() {

            private View nreLogo = findsView.findViewById(R.id.powered_by_nre);

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

        });
        TrackMyTrainApp.instance.attach(new ServiceView() {

            View container = findsView.findViewById(R.id.trackedService);
            TextView scheduledTime = (TextView) container.findViewById(R.id.scheduledtime);
            TextView expectedTime = (TextView) container.findViewById(R.id.estimatedtime);
            TextView platorm = (TextView) container.findViewById(R.id.platform);

            @Override
            public void present(final TrainViewModel train) {
                container.post(new Runnable() {
                    @Override
                    public void run() {
                        scheduledTime.setText(train.scheduledTime());
                        expectedTime.setText(train.estimatedTime());
                        platorm.setText(train.platform());
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
        });
    }

    public void tearDown() {
        TrackMyTrainApp.instance.detach(departureView);
        TrackMyTrainApp.instance.detach(departuresQueryView);
        departureView=null;
        departuresQueryView=null;
    }
}
