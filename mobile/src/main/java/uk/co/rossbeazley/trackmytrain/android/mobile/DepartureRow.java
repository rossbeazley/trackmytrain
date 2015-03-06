package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        onTimeIndicator = findViewById(R.id.ontimeindicator);
        scheduledTime = (TextView) findViewById(R.id.scheduledtime);
        estimatedTime = (TextView) findViewById(R.id.estimatedtime);
        platform = (TextView) findViewById(R.id.platform);
    }


    public void bind(TrainViewModel trainViewModel) {
        scheduledTime.setText(trainViewModel.scheduledTime());
        estimatedTime.setText(trainViewModel.estimatedTime());
        platform.setText(trainViewModel.platform());

    }
}
