package uk.co.rossbeazley.trackmytrain.android;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.departures.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.DeparturesQueryViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class Departures {

    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private List<DeparturesView> departuresViews;

    private final ArrayList<DeparturesQueryView> departuresQueryViews;
    public DepartureQueryCommand departureQueryCommand;

    public Departures(KeyValuePersistence keyValuePersistence, TrainRepository trainRepository) {

        this.trainRepository = trainRepository;
        this.departuresViews = new ArrayList<>(2);
        this.departuresQueryViews = new ArrayList<>();
        stationRepository = new StationRepository(keyValuePersistence);
        departureQueryCommand = new DepartureQueryCommand(this.trainRepository, stationRepository);
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
        Success success = new Success() {
            @Override
            public void success(List<Train> expectedList) {
                departuresFound(expectedList);
            }
        };
        departureQueryCommand.invoke(at, direction, success);
    }

    public void attach(DeparturesQueryView departuresQueryView) {
        departuresQueryView.present(new DeparturesQueryViewModel(stationRepository.getCurrentAt(), stationRepository.getCurrentDirection()));
        this.departuresQueryViews.add(departuresQueryView);
    }

    public void detach(DeparturesQueryView departuresQueryView) {
        this.departuresQueryViews.remove(departuresQueryView);
    }


    public static class DepartureQueryCommand {

        private final TrainRepository trainRepository;
        private final StationRepository stationRepository;

        public DepartureQueryCommand(TrainRepository trainRepository, StationRepository stationRepository) {
            this.trainRepository = trainRepository;
            this.stationRepository = stationRepository;
        }

        public void invoke(Station at, Direction direction, final Success success) {
            stationRepository.setCurrentAt(at);
            stationRepository.setCurrentDirection(direction);
            this.trainRepository.departures(at,direction, new TrainRepository.DeparturesSuccess() {
                @Override
                public void result(List<Train> expectedList) {
                    success.success(expectedList);
                }
            });
        }
    }

    public static interface Success {
        public abstract void success(List<Train> expectedList);
    }
}
