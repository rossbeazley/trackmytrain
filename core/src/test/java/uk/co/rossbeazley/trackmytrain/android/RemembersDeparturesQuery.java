package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RemembersDeparturesQuery {

    @Test @Ignore("ignore")
    public void theOneWhereTheDirectionIsRemembered() {
        TrackMyTrain tmt;
        tmt = new TMTBuilder()
                .with(new NetworkClient() {
                    @Override
                    public void requestString(Request request, Response response) {

                    }
                })
                .with(new NarrowScheduledExecutorService() {
                    @Override
                    public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
                        return null;
                    }
                })
                .build();

        Direction expectedDirection = Direction.to(Station.fromString("SLD"));
        Station at = Station.fromString("ANY");
        tmt.departures(at,expectedDirection);

        CapturingDeparturesQueryView departuresQueryView = new CapturingDeparturesQueryView();
        tmt.attach(departuresQueryView);

        assertThat(departuresQueryView.direction, is(expectedDirection));

    }

    private static class CapturingDeparturesQueryView implements DeparturesQueryView {
        public Station at;
        public Direction direction;

        @Override
        public void present(Station at, Direction direction) {
            this.at = at;
            this.direction = direction;
        }

    }
}
