package uk.co.rossbeazley.trackmytrain.android;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.departures.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.DeparturesQueryViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

/**
 * Created by beazlr02 on 13/03/2015.
 */
public class Departures {

    private final KeyValuePersistence keyValuePersistence;
    private final TrainRepository trainRepository;
    private List<DeparturesView> departuresViews;

    private final ArrayList<DeparturesQueryView> departuresQueryViews;

    public Departures(KeyValuePersistence keyValuePersistence, TrainRepository trainRepository) {
        this.keyValuePersistence = keyValuePersistence;
        this.trainRepository = trainRepository;
        this.departuresViews = new ArrayList<>(2);
        this.departuresQueryViews = new ArrayList<>();
    }

    public void attach(DeparturesView departureView) {
        this.departuresViews.add(departureView);
    }


    public void detach(DeparturesView departuresView) {
        this.departuresViews.remove(departuresView);
    }


    public void presentDepartures(List<Train> expectedList) {
        for (DeparturesView departuresView : departuresViews) {
            departuresView.present(TrainViewModel.list(expectedList));
        }
    }

    public void departures(Station at, Direction direction) {

        this.setCurrentAt(at);
        this.setCurrentDirection(direction);
        this.trainRepository.departures(at,direction, new TrainRepository.DeparturesSuccess() {
            @Override
            public void result(List<Train> expectedList) {
                presentDepartures(expectedList);
            }
        });
    }



    private Direction getCurrentDirection() {
        String stationCode = this.keyValuePersistence.get("direction");
        return Direction.to(Station.fromString(stationCode));
    }

    private void setCurrentDirection(Direction currentDirection) {
        this.keyValuePersistence.put("direction",currentDirection.station().stationCode());
    }

    private Station getCurrentAt() {
        String stationCode = this.keyValuePersistence.get("at");
        return Station.fromString(stationCode);
    }

    private void setCurrentAt(Station currentAt) {
        this.keyValuePersistence.put("at",currentAt.stationCode());
    }



    public void attach(DeparturesQueryView departuresQueryView) {
        departuresQueryView.present(new DeparturesQueryViewModel(this.getCurrentAt(), this.getCurrentDirection()));
        this.departuresQueryViews.add(departuresQueryView);
    }

    public void detach(DeparturesQueryView departuresQueryView) {
        this.departuresQueryViews.remove(departuresQueryView);
    }


}
