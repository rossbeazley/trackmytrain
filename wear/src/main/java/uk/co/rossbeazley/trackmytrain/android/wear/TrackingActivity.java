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
import uk.co.rossbeazley.trackmytrain.android.WearAppSingleton;

public class TrackingActivity extends WearableActivity implements CanFinishWearApp {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        setAmbientEnabled();

        //inflate tracking view
        //attach to app core

        //attach "view" that will finish activity when tracking stops

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
