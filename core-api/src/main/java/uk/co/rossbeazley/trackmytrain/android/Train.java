package uk.co.rossbeazley.trackmytrain.android;

public class Train {
    public final String id;
    public final String scheduledTime;
    public final String estimatedTime;
    public final String platform;
    public final boolean departed;

    public Train(String id, String estimatedTime, String scheduledTime, String platform, boolean departed)
    {
        this.id = id;
        this.estimatedTime = estimatedTime;
        this.scheduledTime = scheduledTime;
        this.platform = platform;
        this.departed = departed;
    }

    public String toString() {
        return scheduledTime + " : " + estimatedTime + " : platform " + platform  + (departed?" : departed : " : " : ") + id + System.getProperty("line.separator");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        if(   (estimatedTime != null ? !estimatedTime.equals(train.estimatedTime) : train.estimatedTime != null)
           || (id != null ? !id.equals(train.id) : train.id != null)
           || (platform != null ? !platform.equals(train.platform) : train.platform != null)
           || (scheduledTime != null ? !scheduledTime.equals(train.scheduledTime) : train.scheduledTime != null)
           || (departed != train.departed))
            return false;

        return true;
    }


    public boolean isLate() {
        return !estimatedTime.equalsIgnoreCase("On Time");
    }

    public boolean departed() {
        return departed;
    }
}
