package uk.co.rossbeazley.trackmytrain.android.wear;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.WearAppSingleton;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

public class TrackingActivity extends WearableActivity implements CanFinishWearApp, FindsView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        setAmbientEnabled();

        //inflate tracking view
        //attach to app core

        //attach "view" that will finish activity when tracking stops

        WearAppSingleton.instance.attach(new TrackingView(this));
        WearAppSingleton.instance.attach(new ExitWearApp(this));
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();
    }


    @Override
    public void finish() {
        super.finish();
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, TrackingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private static class TrackingView implements ServiceView {


        private final FindsView findsView;
        private final TextView scheduledtime;
        private final TextView estimatedtime;
        private final TextView platform;

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

        }

        @Override
        public void trackingStarted() {

        }
    }
}
