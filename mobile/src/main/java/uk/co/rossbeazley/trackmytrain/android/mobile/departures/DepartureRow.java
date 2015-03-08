package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

@TargetApi(21)
public class DepartureRow extends LinearLayout {
    private View onTimeIndicator;
    private TextView scheduledTime;
    private TextView estimatedTime;
    private TextView platform;

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
        return this;
    }

    static public DepartureRow createDepartureRow(ViewGroup parent) {
        DepartureRow row;LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        row = recycleDepartureRow((DepartureRow) inflater.inflate(R.layout.departurerow, parent, false));
        return row;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        onTimeIndicator = findViewById(R.id.ontimeindicator);
        scheduledTime = (TextView) findViewById(R.id.scheduledtime);
        estimatedTime = (TextView) findViewById(R.id.estimatedtime);
        platform = (TextView) findViewById(R.id.platform);
    }


    public DepartureRow bind(TrainViewModel trainViewModel) {
        scheduledTime.setText(trainViewModel.scheduledTime());
        estimatedTime.setText(trainViewModel.estimatedTime());
        platform.setText(trainViewModel.platform());
        return this;
    }
}
