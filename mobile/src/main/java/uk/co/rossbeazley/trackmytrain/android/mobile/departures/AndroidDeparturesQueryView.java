package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.DeparturesQueryViewModel;
import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

class AndroidDeparturesQueryView implements DeparturesQueryView {
    private final AutoCompleteTextView at;
    private final AutoCompleteTextView to;
    private DeparturesQueryViewModel departuresQueryViewModel;

    public AndroidDeparturesQueryView(FindsView findsView) {
        at = (AutoCompleteTextView) findsView.findViewById(R.id.at);
        to = (AutoCompleteTextView) findsView.findViewById(R.id.to);

        findsView.findViewById(R.id.getdepartures).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Station at = departuresQueryViewModel.getAt();
                Direction direction = departuresQueryViewModel.getDirection();
                TrackMyTrainApp.instance.departures(at, direction);
            }
        });
    }

    @Override
    public void present(final DeparturesQueryViewModel departuresQueryViewModel) {
        this.departuresQueryViewModel = departuresQueryViewModel;
        presentTo(departuresQueryViewModel);
        presentAt(departuresQueryViewModel);
    }

    private void presentAt(final DeparturesQueryViewModel departuresQueryViewModel) {
        ArrayAdapter<Station> adapter = new ArrayAdapter<Station>(at.getContext(), android.R.layout.simple_list_item_1, departuresQueryViewModel.stations());
        at.setAdapter(adapter);
        at.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Station station = (Station) parent.getAdapter().getItem(position);
                departuresQueryViewModel.setAt(station);
            }
        });
        if (departuresQueryViewModel.getAt() != null) {
            at.post(new Runnable() {
                @Override
                public void run() {
                    at.setText(
                            departuresQueryViewModel.getAt().toString());
                }
            });
        }
    }

    private void presentTo(final DeparturesQueryViewModel departuresQueryViewModel) {
        ArrayAdapter<Station> adapter = new ArrayAdapter<Station>(to.getContext(), android.R.layout.simple_list_item_1, departuresQueryViewModel.stations());
        to.setAdapter(adapter);
        to.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Station station = (Station) parent.getAdapter().getItem(position);
                departuresQueryViewModel.setDirection(Direction.to(station));
            }
        });

        if(departuresQueryViewModel.getDirection()!=null && departuresQueryViewModel.getDirection().station()!=null) {
            to.post(new Runnable() {
                @Override
                public void run() {
                    to.setText(departuresQueryViewModel.getDirection().station().toString());
                }
            });
        }


    }
}
