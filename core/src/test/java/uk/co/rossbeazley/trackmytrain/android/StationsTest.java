package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.departures.Stations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StationsTest {

    @Test
    public void
    theOneWhereAStationIsFoundFromPartOfItsName() {
        Station foundStation = Stations.searchFor("Buckshaw");
        assertThat(foundStation,is(Stations.fromStationCode("BSV")));
    }

    @Test
    public void
    theOneWhereAStationIsFoundFromToToStringOfItself() {
        Station bsv = Stations.fromStationCode("BSV");
        Station foundStation = Stations.searchFor(bsv.toString());
        assertThat(foundStation,is(bsv));
    }

    @Test
    public void
    theOneWhereAStationIsFoundFromItsWholeNameWhereAnotherStationContainsThatName() {
        Station foundStation = Stations.searchFor("Bolton");
        assertThat(foundStation,is(Stations.fromStationCode("BON")));
    }


    @Test
    public void
    theOneWhereAStationIsFoundFromItsStationCodeOnly() {
        Station foundStation = Stations.searchFor("BON");
        assertThat(foundStation,is(Stations.fromStationCode("BON")));
    }

    @Test
    public void
    theUnknownStation() {
        assertThat(Stations.searchFor("not a match"),is(Stations.UNKNOWN));
    }

}
