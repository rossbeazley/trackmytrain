package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.co.rossbeazley.trackmytrain.android.CanPresentDepartureQueries;
import uk.co.rossbeazley.trackmytrain.android.CanQueryDepartures;
import uk.co.rossbeazley.trackmytrain.android.DepartureQueryCommands;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

public class DeparturesPresenter implements CanPresentDepartureQueries, DepartureQueryCommands {

    private List<DeparturesView> departuresViews;

    private final List<DeparturesQueryView> departuresQueryViews;
    public CanQueryDepartures canQueryDepartures;
    private final CanQueryDepartures.DepartureQueryListener resultCallback;

    public DeparturesPresenter(CanQueryDepartures canQueryDepartures) {

        this.departuresViews = new CopyOnWriteArrayList<>();
        this.departuresQueryViews = new CopyOnWriteArrayList<>();
        this.canQueryDepartures = canQueryDepartures;
        resultCallback = new CanQueryDepartures.DepartureQueryListener() {
            @Override
            public void success(List<Train> expectedList) {
                departuresFound(expectedList);
            }

            @Override
            public void error(TMTError tmtError) {
                departuresError(tmtError);
            }

            @Override
            public void loading() {

            }
        };
    }

    public void attach(DeparturesView departureView) {
        this.departuresViews.add(departureView);
    }


    public void detach(DeparturesView departuresView) {
        this.departuresViews.remove(departuresView);
    }

    private void departuresFound(List<Train> expectedList) {
        for (DeparturesView departuresView : departuresViews) {
            departuresView.present(DeparturesViewModel.fromListOfTrains(expectedList));
        }
    }

    public void departures(Station at, Direction direction) {
        showLoading();
        canQueryDepartures.departures(at, direction, resultCallback);
    }

    @Override
    public void departures(DepartureQuery query) {
        departures(query.at(), query.direction());
    }

    void departuresError(TMTError tmtError) {
        for (DeparturesView departuresView : departuresViews) {
            departuresView.error(tmtError);
        }
    }

    void showLoading() {
        for (DeparturesView departuresView : departuresViews) {
            departuresView.loading();
        }
    }

    public void attach(DeparturesQueryView departuresQueryView) {
        departuresQueryView.attach(this);
        DepartureQuery departureQuery = canQueryDepartures.lastQuery();
        departuresQueryView.present(new DeparturesQueryViewModel(departureQuery));
        this.departuresQueryViews.add(departuresQueryView);
    }

    public void detach(DeparturesQueryView departuresQueryView) {
        this.departuresQueryViews.remove(departuresQueryView);
        departuresQueryView.detach(this);
    }


}
