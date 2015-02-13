package uk.co.rossbeazley.trackmytrain.android;

public class Train {
    public final String scheduledTime;
    public final String estimatedTime;
    public final String id;
    public final String platform;

    public Train(String id, String estimatedTime, String scheduledTime)
    {
        this.id = id;
        this.estimatedTime = estimatedTime;
        this.scheduledTime = scheduledTime;
        this.platform = "";
    }

    public String toString() {
        return scheduledTime + " : " + estimatedTime + " : " + id + System.getProperty("line.separator");
    }

}
