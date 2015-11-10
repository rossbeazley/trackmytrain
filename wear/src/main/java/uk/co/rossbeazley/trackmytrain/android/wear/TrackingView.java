package uk.co.rossbeazley.trackmytrain.android.wear;

import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.WearAppSingleton;

/**
 * Created by beazlr02 on 13/07/2015.
 */
class TrackingView implements ServiceView {


    private final TextView scheduledtime;
    private final TextView estimatedtime;
    private final TextView platform;
    private final FindsView findsView;

    public TrackingView(FindsView findsView) {
        this.findsView = findsView;

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
        WearAppSingleton.instance.detach(this);
    }

    @Override
    public void trackingStarted() {

    }

    public void colour() {
        findsView.findViewById(R.id.container).setBackgroundColor(0xff559B91);
        findsView.findViewById(R.id.clock_background).setBackgroundColor(0xffffffff);
        ((TextView) findsView.findViewById(R.id.clock)).setTextColor(0xffff0000);
    }

    public void grey() {
        findsView.findViewById(R.id.container).setBackgroundColor(0xff000000);
        findsView.findViewById(R.id.clock_background).setBackgroundColor(0xff000000);
        ((TextView) findsView.findViewById(R.id.clock)).setTextColor(0xffaaaaaa);
    }
}
