package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQueryCommand;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

public class DeparturesPresenter {

    private List<DeparturesView> departuresViews;

    private final List<DeparturesQueryView> departuresQueryViews;
    public TrackMyTrain trackMyTrain;
    private final DepartureQueryCommand.Success resultCallback;

    public DeparturesPresenter(TrackMyTrain trackMyTrain) {

        this.departuresViews = new CopyOnWriteArrayList<>();
        this.departuresQueryViews = new CopyOnWriteArrayList<>();
        this.trackMyTrain = trackMyTrain;
        resultCallback = new DepartureQueryCommand.Success() {
            @Override
            public void success(List<Train> expectedList) {
                departuresFound(expectedList);
            }

            @Override
            public void error(TMTError tmtError) {
                departuresError(tmtError);
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
            departuresView.present(TrainViewModel.list(expectedList));
        }
    }

    public void departures(Station at, Direction direction) {
        showLoading();
        trackMyTrain.invoke(at, direction, resultCallback);
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
        DepartureQuery departureQuery = trackMyTrain.lastQuery();
        departuresQueryView.present(new DeparturesQueryViewModel(departureQuery));
        this.departuresQueryViews.add(departuresQueryView);
    }

    public void detach(DeparturesQueryView departuresQueryView) {
        this.departuresQueryViews.remove(departuresQueryView);
    }


}
