package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.CanProcessPresentTrackedTrainsCommands;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

class TrackedServiceView implements ServiceView {

    private final FindsView findsView;
    private final CanProcessPresentTrackedTrainsCommands canProcessPresentTrackedTrainsCommands;
    View container;
    TextView scheduledTime;
    TextView expectedTime;
    TextView platorm;
    Button stopTracking;

    public TrackedServiceView(FindsView findsView, CanProcessPresentTrackedTrainsCommands canProcessPresentTrackedTrainsCommands) {
        this.findsView = findsView;
        container = findsView.findViewById(R.id.trackedService);
        scheduledTime = (TextView) container.findViewById(R.id.scheduledtime);
        expectedTime = (TextView) container.findViewById(R.id.estimatedtime);
        platorm = (TextView) container.findViewById(R.id.platform);
        stopTracking = (Button) container.findViewById(R.id.trackbutton);
        this.canProcessPresentTrackedTrainsCommands = canProcessPresentTrackedTrainsCommands;
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
                        canProcessPresentTrackedTrainsCommands.unwatch();
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
                scheduledTime.setText("");
                expectedTime.setText("");
                platorm.setText("loading...");
                container.setVisibility(View.VISIBLE);
            }
        });

    }
}
