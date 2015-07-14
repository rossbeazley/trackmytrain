package uk.co.rossbeazley.trackmytrain.android.wear;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.WearAppSingleton;

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

}
