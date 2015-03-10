package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.DeparturesQueryViewModel;
import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

class AndroidDeparturesQueryView implements DeparturesQueryView {
    private final TextView at;
    private final TextView to;

    public AndroidDeparturesQueryView(FindsView findsView) {
        at = (TextView) findsView.findViewById(R.id.from);
        to = (TextView) findsView.findViewById(R.id.to);

        findsView.findViewById(R.id.getdepartures).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Station at = Station.fromString(String.valueOf(AndroidDeparturesQueryView.this.at.getText()));
                Direction direction = Direction.to(Station.fromString(String.valueOf(to.getText())));
                TrackMyTrainApp.instance.departures(at, direction);
            }
        });
    }

    @Override
    public void present(final DeparturesQueryViewModel departuresQueryViewModel) {

        if (departuresQueryViewModel.getAt() != null) {
            at.post(new Runnable() {
                @Override
                public void run() {
                    at.setText(departuresQueryViewModel.getAt().toString());
                }
            });
        }


        to.post(new Runnable() {
            @Override
            public void run() {
                to.setText(departuresQueryViewModel.getDirection().station().toString());
            }
        });
    }
}
