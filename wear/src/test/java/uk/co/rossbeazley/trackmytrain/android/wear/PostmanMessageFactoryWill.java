package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostmanMessageFactoryWill {

    @Test public void
    convertMessageEventToIAMBASEMessage() {


        final String anyId = "anyId";
        Postman.BroadcastMessage expectedMessage = new IAmBaseMessage(new Postman.NodeId(anyId));
        MessageEvent msg = new WearMessageEvent(expectedMessage.messageAsString(), anyId);

        Postman.BroadcastMessage convertedMessage = new PostmanMessageFactory().toBroadcastMessage(msg);
        assertThat(convertedMessage, is(equalTo(expectedMessage)));
    }







    private static class PostmanMessageFactory {

        private Map<String, MessageFactory> factoriesFromPath;

        public PostmanMessageFactory() {
            factoriesFromPath = new HashMap<>();
            factoriesFromPath.put(IAmBaseMessage.MESSAGE_PATH, new IAmBaseMessageFactory());
        }


        public Postman.Message toMessage(MessageEvent messageEvent) {
            return null;
        }

        public Postman.BroadcastMessage toBroadcastMessage(MessageEvent messageEvent) {
            final MessageFactory messageFactory = factoriesFromPath.get(messageEvent.getPath());
            return messageFactory.create(messageEvent);
        }



        private static class IAmBaseMessageFactory implements MessageFactory {
            @Override
            public Postman.BroadcastMessage create(MessageEvent messageEvent) {
                Postman.NodeId nodeId = new Postman.NodeId(messageEvent.getSourceNodeId());
                return new IAmBaseMessage(nodeId);
            }
        }

        public static interface MessageFactory {
            Postman.BroadcastMessage create(MessageEvent messageEvent);
        }
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
