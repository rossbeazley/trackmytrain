package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostmanMessageFactoryWill {

    @Test public void
    convertMessageEventToIAMBASEMessage() {


        final String anyId = "anyId";
        Postman.Message expectedMessage = new IAmBaseMessage(new Postman.NodeId(anyId));
        MessageEvent msg = new WearMessageEvent(expectedMessage.messageAsString(), anyId);

        Postman.Message convertedMessage = new PostmanMessageFactory().toMessage(msg);
        assertThat(convertedMessage, is(equalTo(expectedMessage)));
    }


    private static class WearMessageEvent implements MessageEvent {

        private String path;
        private String sourceNodeId;

        private WearMessageEvent(String path, String sourceNodeId) {

            this.path = path;
            this.sourceNodeId = sourceNodeId;
        }

        @Override
        public int getRequestId() {
            return 0;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public byte[] getData() {
            return new byte[0];
        }

        @Override
        public String getSourceNodeId() {
            return sourceNodeId;
        }
    }
}
