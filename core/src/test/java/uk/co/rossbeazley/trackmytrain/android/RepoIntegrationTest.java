package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import java.util.List;

public class RepoIntegrationTest {

    @Test
    public void services() throws InterruptedException {
        TrainRepo repo = new TrainRepo();
        repo.departures(new TrainRepo.DeparturesSuccess() {
            @Override
            public void ok(List<Train> object) {
                System.out.printf(object.toString());
            }
        });
        Thread.sleep(2000);
    }

    @Test
    public void serivce() throws InterruptedException {
        TrainRepo repo = new TrainRepo();
        String serviceId = "KcytvjUNuZht1UllqBcS3g==";
        repo.service(serviceId, new TrainRepo.ServiceSuccess() {
            @Override
            public void ok(Train object) {
                System.out.printf(object.toString());
            }
        });
        Thread.sleep(2000);
    }
}
