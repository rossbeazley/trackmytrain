package uk.co.rossbeazley.trackmytrain.android.favorites;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FavoritesPresenterDoes {

    @Test
    public void
    storesDeparturesQueryToFavorites() {



        DepartureQuery storedQuery = null;
        DepartureQuery expectedQuery = null;
        assertThat(storedQuery, is(expectedQuery));

    }
}
