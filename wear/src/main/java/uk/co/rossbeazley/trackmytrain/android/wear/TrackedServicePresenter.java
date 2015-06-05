package uk.co.rossbeazley.trackmytrain.android.wear;

import android.widget.TextView;

import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;

/**
 * Created by beazlr02 on 30/05/2015.
 */
class TrackedServicePresenter implements DataApi.DataListener {
    private final TextView platform;
    private final TextView time;
    private final TextView lateness;

    public TrackedServicePresenter(TextView platform, TextView time, TextView lateness) {

        this.platform = platform;
        this.time = time;
        this.lateness = lateness;
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {

                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/trackedservice") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();

                    bindView(
                            dataMap.getString("PLATFORM", "unknown"),
                            dataMap.getString("TIME", "--/--"),
                            dataMap.getString("LATENESS", "unknown"));

                }
            }

        }
    }

    private void bindView(String platform, String time, String lateness) {
        this.platform.setText(platform);
        this.time.setText(time);
        this.lateness.setText(lateness);
    }
}
