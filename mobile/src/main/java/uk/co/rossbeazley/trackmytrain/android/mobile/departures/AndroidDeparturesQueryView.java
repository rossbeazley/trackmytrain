package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

class AndroidDeparturesQueryView implements DeparturesQueryView {
    private final AutoCompleteTextView at;
    private final AutoCompleteTextView to;
    private final TextView at_compact;
    private final TextView to_compact;
    private final ViewGroup servicedetailsRoot;
    private DeparturesQueryViewModel departuresQueryViewModel;
    private View departureQueryView;
    private final View departureQueryViewCompact;

    public AndroidDeparturesQueryView(final FindsView findsView) {
        at = (AutoCompleteTextView) findsView.findViewById(R.id.at);
        to = (AutoCompleteTextView) findsView.findViewById(R.id.to);

        at_compact = (TextView) findsView.findViewById(R.id.at_compact);
        to_compact = (TextView) findsView.findViewById(R.id.to_compact);

        departureQueryView = findsView.findViewById(R.id.departure_query_view);
        departureQueryViewCompact = findsView.findViewById(R.id.departure_query_view_compact);

        servicedetailsRoot = (ViewGroup) findsView.findViewById(R.id.servicedetails);

        findsView.findViewById(R.id.getdepartures).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchQuery();
                showCompactQueryBar();
            }
        });

        departureQueryViewCompact.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showFullQueryBar();
                return false;
            }
        });
    }

    void showFullQueryBar() {
        prepareForTransitionAnimation();
        departureQueryView.setVisibility(View.VISIBLE);
        departureQueryViewCompact.setVisibility(View.GONE);
    }

    void showCompactQueryBar() {
        prepareForTransitionAnimation();
        departureQueryView.setVisibility(View.GONE);
        departureQueryViewCompact.setVisibility(View.VISIBLE);
    }

    private void prepareForTransitionAnimation() {
        TransitionManager.beginDelayedTransition(servicedetailsRoot, new AutoTransition().setDuration(100));
    }

    void dispatchQuery() {
        Station at = departuresQueryViewModel.getAt();
        Direction direction = departuresQueryViewModel.getDirection();
        TrackMyTrainApp.instance.departures(at, direction);
    }
    @Override
    public void present(final DeparturesQueryViewModel departuresQueryViewModel) {
        this.departuresQueryViewModel = departuresQueryViewModel;
        presentStationNames(departuresQueryViewModel);

        presentStationLists(departuresQueryViewModel);
    }

    void presentStationLists(DeparturesQueryViewModel departuresQueryViewModel) {
        presentToStationList(departuresQueryViewModel);
        presentAtStationList(departuresQueryViewModel);
    }

    void presentStationNames(DeparturesQueryViewModel departuresQueryViewModel) {
        presentTo(departuresQueryViewModel);
        presentAt(departuresQueryViewModel);
    }

    private void presentAt(final DeparturesQueryViewModel departuresQueryViewModel) {
        if (departuresQueryViewModel.getAt() != null) {
            at.post(new Runnable() {
                @Override
                public void run() {
                    final String stationName = departuresQueryViewModel.getAt().toString();
                    at.setText(stationName);
                    at_compact.setText(stationName);
                }
            });
        }
    }

    private void presentAtStationList(final DeparturesQueryViewModel departuresQueryViewModel) {
        ArrayAdapter<Station> adapter = new ArrayAdapter<Station>(at.getContext(), android.R.layout.simple_list_item_1, departuresQueryViewModel.stations());
        at.setAdapter(adapter);
        at.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Station station = (Station) parent.getAdapter().getItem(position);
                departuresQueryViewModel.setAt(station);
                presentStationNames(departuresQueryViewModel);
            }
        });
    }

    private void presentTo(final DeparturesQueryViewModel departuresQueryViewModel) {
        if(departuresQueryViewModel.getDirection()!=null && departuresQueryViewModel.getDirection().station()!=null) {
            to.post(new Runnable() {
                @Override
              public void run() {
                    final String stationName = departuresQueryViewModel.getDirection().station().toString();
                    to.setText(stationName);
                    to_compact.setText(stationName);
                }
            });
        }


    }

    private void presentToStationList(final DeparturesQueryViewModel departuresQueryViewModel) {
        ArrayAdapter<Station> adapter = new ArrayAdapter<Station>(to.getContext(), android.R.layout.simple_list_item_1, departuresQueryViewModel.stations());
        to.setAdapter(adapter);
        to.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Station station = (Station) parent.getAdapter().getItem(position);
                departuresQueryViewModel.setDirection(Direction.to(station));
                presentStationNames(departuresQueryViewModel);
            }
        });
    }
}
