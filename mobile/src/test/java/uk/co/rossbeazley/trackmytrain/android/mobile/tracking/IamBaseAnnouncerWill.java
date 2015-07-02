package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by beazlr02 on 02/07/2015.
 */
public class IamBaseAnnouncerWill {

    @Test public void
    announceBaseNodeIdWhenTrackingStarts() {



        Postman.Message messageDelivered = null;
        Postman.Message expectedMessage = null;
        assertThat(messageDelivered,is(expectedMessage));
    }
}
