package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostmanMessageFactoryWill {

    @Test public void
    putMessageInEnvolopeWithReturnAddress() {


        final String anyId = "anyId";
        Postman.Message expectedMessage = new IAmBaseMessage();
        MessageEvent msg = new WearMessageEvent(expectedMessage.messageAsString(), anyId);

        MessageEnvelope convertedMessage = new PostmanMessageFactory().toMessage(msg);
        Postman.NodeId expectedId = new Postman.NodeId(anyId);
        assertThat(convertedMessage.fromId(), is(equalTo(expectedId)));
    }

    @Test
    public void
    convertMessageEventToIAMBASEMessage() {


        final String anyId = "anyId";
        Postman.Message expectedMessage = new IAmBaseMessage();
        MessageEvent msg = new WearMessageEvent(expectedMessage.messageAsString(), anyId);

        MessageEnvelope convertedMessage = new PostmanMessageFactory().toMessage(msg);
        assertThat(convertedMessage.message(), is(equalTo(expectedMessage)));
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
