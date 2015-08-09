package uk.co.rossbeazley.trackmytrain.wsclient;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * departures: train and service
 * ID : serviceID
 * scheduled time (choose arrival as primary but departure when null or empty) : scheduledTime/std
 * estimated time : scheduledTime/etd
 * platform : platform
 *
 */
@XmlRootElement
public class Train {

    public final String id;
    public final String scheduledTime;
    public final String estimatedTime;
    public final String platform;
    private final String error;
    private final boolean departed;

    public Train(String id, String scheduledTime, String estimatedTime, String platform, String error, boolean departed) {
        this.id = id;
        this.scheduledTime = scheduledTime;
        this.estimatedTime = estimatedTime;
        this.platform = platform;
        this.error=error;
        this.departed = departed;
    }

    public static Train ok(String id, String scheduledTime, String estimatedTime, String platform, boolean departed) {
        return new Train(id, scheduledTime, estimatedTime, platform != null ? platform : "", "", departed);
    }

    @Override
    public String toString() {
        return scheduledTime + " " + estimatedTime + " error?" + error;
    }

    public String toJson() {
        return "{\n" +
                "\"id\":\""+ id +"\",\n" +
                "\"scheduledTime\":\""+ scheduledTime +"\",\n" +
                "\"estimatedTime\":\""+ estimatedTime +"\",\n" +
                "\"platform\":\"" + platform + "\"" +
                (departed ? ",\n\"departed\":true" : "") +
                "\n}\n";
    }
}
