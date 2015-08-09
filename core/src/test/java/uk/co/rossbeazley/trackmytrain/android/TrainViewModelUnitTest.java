package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by beazlr02 on 16/07/2015.
 */
public class TrainViewModelUnitTest {

    @Test
    public void aTrainWithoutAPlatformIsHyphen() {
        Train train = new Train("id", "02:01", "02:00", "", false);

        assertThat(new TrainViewModel(train).platform(),is("Platform -"));
    }

    @Test
    public void aTrainWithNullPlatformIsHyphen() {
        Train train = new Train("id", "02:01", "02:00", null, false);

        assertThat(new TrainViewModel(train).platform(),is("Platform -"));
    }


}
