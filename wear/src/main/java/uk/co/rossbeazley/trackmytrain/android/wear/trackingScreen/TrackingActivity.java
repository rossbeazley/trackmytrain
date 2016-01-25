package uk.co.rossbeazley.trackmytrain.android.wear.trackingScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.wear.WearAppSingleton;

public class TrackingActivity extends WearableActivity implements CanFinishWearApp, FindsView {

    private TrackingView serviceView;
    private ExitTrackingScreen exitWearApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        setAmbientEnabled();

        serviceView = new TrackingView(this);
        exitWearApp = new ExitTrackingScreen(this);

        bind();
    }

    private void bind() {
        WearAppSingleton.instance.attach(serviceView);
        WearAppSingleton.instance.attach(exitWearApp);
    }

    @Override
    protected void onDestroy() {
        unbind();
        super.onDestroy();
    }

    private void unbind() {
        WearAppSingleton.instance.detach(serviceView);
        WearAppSingleton.instance.detach(exitWearApp);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        serviceView.grey();
        unbind();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        bind();
        unbind();
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();
        serviceView.colour();
        bind();

    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, TrackingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
