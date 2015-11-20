package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LateTrainTest {

    @Test
    public void aLateTrain() {

        Train train = new Train("anyId", "20:24", "20:22", "4", false);
        TrainViewModel tvm = new TrainViewModel(train);
        assertThat(tvm.isLate(),is(true));
    }
}
