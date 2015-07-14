package uk.co.rossbeazley.trackmytrain.android.wear;

import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

/**
 * Created by beazlr02 on 13/07/2015.
 */
class TrackingView implements ServiceView {


    private final TextView scheduledtime;
    private final TextView estimatedtime;
    private final TextView platform;

    public TrackingView(FindsView findsView) {

        scheduledtime = (TextView) findsView.findViewById(R.id.scheduledtime);
        estimatedtime = (TextView) findsView.findViewById(R.id.estimatedtime);
        platform = (TextView) findsView.findViewById(R.id.platform);


    }

    @Override
    public void present(final TrainViewModel train) {

        scheduledtime.post(new Runnable() {
            @Override
            public void run() {
                scheduledtime.setText(train.scheduledTime());
                estimatedtime.setText(train.estimatedTime());
                platform.setText(train.platform());

            }
        });
    }

    @Override
    public void hide() {

    }

    @Override
    public void trackingStarted() {

    }
}
