package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

@TargetApi(21)
public class DepartureRow extends LinearLayout {
    private View onTimeIndicator;
    private TextView scheduledTime;
    private TextView estimatedTime;
    private TextView platform;
    private Button trackButton;

    public DepartureRow(Context context) {
        super(context);
    }

    public DepartureRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DepartureRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DepartureRow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    static public DepartureRow recycleDepartureRow(View convertView) {
        final DepartureRow departureRow = (DepartureRow) convertView;
        return departureRow.clear();
    }

    private DepartureRow clear() {
        scheduledTime.setText("");
        estimatedTime.setText("");
        platform.setText("");
        trackButton.setTag(null);
        trackButton.setVisibility(GONE);
        final int color = loadColourInt(R.color.dark_green);
        onTimeIndicator.setBackgroundColor(color);
        return this;
    }

    private int loadColourInt(int id) {
        final Resources resources = onTimeIndicator.getContext().getResources();
        return resources.getColor(id);
    }

    static public DepartureRow createDepartureRow(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return (DepartureRow) inflater.inflate(R.layout.departurerow, parent, false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        onTimeIndicator = findViewById(R.id.ontimeindicator);
        scheduledTime = (TextView) findViewById(R.id.scheduledtime);
        estimatedTime = (TextView) findViewById(R.id.estimatedtime);
        platform = (TextView) findViewById(R.id.platform);
        trackButton = (Button) findViewById(R.id.trackbutton);
        trackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackMyTrainApp.instance.watch(((String) v.getTag()));
            }
        });
    }


    public DepartureRow bind(TrainViewModel trainViewModel, TrainViewModel selectedTrain) {
        scheduledTime.setText(trainViewModel.scheduledTime());
        estimatedTime.setText(trainViewModel.estimatedTime());
        platform.setText(trainViewModel.platform());
        trackButton.setTag(trainViewModel.id());
        final int color = loadColourInt(trainViewModel.isLate() ? R.color.dark_yellow : R.color.dark_green );
        onTimeIndicator.setBackgroundColor(color);
        int vis = trainViewModel.equals(selectedTrain) ? VISIBLE : GONE;
        trackButton.setVisibility(vis);
        return this;
    }
}
