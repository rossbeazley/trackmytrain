package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.Station;

class AndroidDeparturesQueryView implements DeparturesQueryView {
    private final TextView from;
    private final TextView to;

    public AndroidDeparturesQueryView(FindsView findsView) {
        from = (TextView) findsView.findViewById(R.id.from);
        to = (TextView) findsView.findViewById(R.id.to);
    }

    @Override
    public void present(final Station at, final Direction direction) {

        from.post(new Runnable() {
            @Override
            public void run() {
                from.setText(at.toString());
            }
        });


        to.post(new Runnable() {
            @Override
            public void run() {
                to.setText(direction.station().toString());
            }
        });
    }
}
