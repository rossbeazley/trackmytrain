package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.content.Context;

import java.util.Objects;

public interface Postman {

    void post(Message message, NodeId deliveryAddress);

    void broadcast(Message message);

    class BroadcastMessage extends Message {

        private final String messagePath;

        protected BroadcastMessage(String messagePath) {
            super(null, messagePath);
            this.messagePath = messagePath;
        }

        public String messageAsString() {
            return messagePath;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BroadcastMessage that = (BroadcastMessage) o;

            return !(messagePath != null ? !messagePath.equals(that.messagePath) : that.messagePath != null);

        }

        @Override
        public int hashCode() {
            return messagePath != null ? messagePath.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "BroadcastMessage{" +
                    "messagePath='" + messagePath + '\'' +
                    '}';
        }
    }

    class Message {

        private final WearPostman.NodeId id;
        private final String messagePath;

        public Message(NodeId id, String messagePath) {
            this.id = id;
            this.messagePath = messagePath;
        }

        public String nodeIdAsString() {
            return id.toString();
        }

        public String messageAsString() {
            return messagePath;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Message message = (Message) o;
            return Objects.equals(id, message.id) &&
                    Objects.equals(messagePath, message.messagePath);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, messagePath);
        }
    }

    class NodeId {
        private String id;

        public NodeId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            return ((NodeId)obj).id.equals(id);
        }
    }


    class Builder {

        static public Postman build(Context context) {
            WearNetwork network = new WearNetwork(context);
            WearPostman postman = new WearPostman(network);
            return new PostOffice(postman,network);
        }
    }
}
