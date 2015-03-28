package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQueryCommand;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

public class DeparturesPresenter {

    private List<DeparturesView> departuresViews;

    private final ArrayList<DeparturesQueryView> departuresQueryViews;
    public DepartureQueryCommand departureQueryCommand;
    private final Success trainRepoResultCallback;

    public DeparturesPresenter(DepartureQueryCommand queryCommand) {

        this.departuresViews = new ArrayList<>(2);
        this.departuresQueryViews = new ArrayList<>();
        departureQueryCommand = queryCommand;
        trainRepoResultCallback = new Success() {
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
        departureQueryCommand.invoke(at, direction, trainRepoResultCallback);
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
        DepartureQuery departureQuery = departureQueryCommand.lastQuery();
        departuresQueryView.present(new DeparturesQueryViewModel(departureQuery));
        this.departuresQueryViews.add(departuresQueryView);
    }

    public void detach(DeparturesQueryView departuresQueryView) {
        this.departuresQueryViews.remove(departuresQueryView);
    }


    public static interface Success {
        public abstract void success(List<Train> expectedList);

        void error(TMTError tmtError);
    }
}
