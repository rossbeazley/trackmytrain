package uk.co.rossbeazley.trackmytrain.android.mobile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import uk.co.rossbeazley.trackmytrain.android.KeyValuePersistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 16)
public class SharedPrefKeyValuePersistenceTest {

    @Test
    public void persistsAndRetreivesData() {
        String expectedValue = "test_data_Stored";
        KeyValuePersistence persistence = new SharedPrefKeyValuePersistence(Robolectric.application);
        final String test_key = "TEST_KEY";
        persistence.put(test_key,expectedValue);
        String value = persistence.get(test_key);
        assertThat(value,is(expectedValue));

    }

}
