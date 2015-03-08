package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.view.View;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

public class OnScreenTrackedServiceView implements ServiceView {

    private TextView trackedTrain;

    OnScreenTrackedServiceView(FindsView findsView) {
        findsView.findViewById(R.id.stopbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackMyTrainApp.instance.unwatch();
            }
        });

        trackedTrain = (TextView) findsView.findViewById(R.id.trackedservice);
    }

    @Override
    public void present(final TrainViewModel train) {
        trackedTrain.post(new Runnable() {
            @Override
            public void run() {
                trackedTrain.setText(train.toString());
            }
        });
    }

    @Override
    public void hide() {
        trackedTrain.post(new Runnable() {
            @Override
            public void run() {
                trackedTrain.setText("");
            }
        });
    }
}
