package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

public class Departures extends Activity implements FindsView {

    private ListViewDeparturesView departureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicedetails);
        departureView = new ListViewDeparturesView(this);
        TrackMyTrainApp.instance.attach(departureView);

        findViewById(R.id.stopbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackMyTrainApp.instance.unwatch();
            }
        });



        TrackMyTrainApp.instance.attach(new ServiceView() {

            private TextView trackedTrain = (TextView) findViewById(R.id.trackedservice);

            @Override
            public void present(final TrainViewModel train) {
                final Runnable action = new Runnable() {
                    @Override
                    public void run() {
                        trackedTrain.setText(train.toString());
                    }
                };
                trackedTrain.post(action);

            }

            @Override
            public void hide() {
                final Runnable action = new Runnable() {
                    @Override
                    public void run() {
                        trackedTrain.setText("");
                    }
                };
                trackedTrain.post(action);
            }
        });


        TrackMyTrainApp.instance.attach(new DeparturesQueryView() {
            @Override
            public void present(Station at, Direction direction) {
                final FindsView findsView = (FindsView) Departures.this;
                ((TextView) findsView.findViewById(R.id.from)).setText(at.toString());
                ((TextView) findsView.findViewById(R.id.to)).setText(direction.station().toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TrackMyTrainApp.instance.detach(departureView);
        departureView=null;
    }
}
