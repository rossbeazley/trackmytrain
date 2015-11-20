package uk.co.rossbeazley.trackmytrain.android;

public class TrainViewModel {

    private final Train train;

    public TrainViewModel(Train train) {
        this(train.id, train.estimatedTime, train.scheduledTime, train.platform);
    }

    public TrainViewModel(String id, String estimatedTime, String scheduledTime, String platform) {
        train = new Train(id, estimatedTime, scheduledTime, platform, false);
    }

    public String toString() {
        return train.toString();
    }

    public boolean equals(Object o) {
        if (o==null) return false;
        TrainViewModel that = (TrainViewModel) o;
        return train.equals(that.train);
    }

    public int hashCode() {
        return train.hashCode();
    }

    public String scheduledTime() {
        return train.scheduledTime;
    }

    public String platform() {
        return String.format("Platform %s", train.platform==null||train.platform.length()==0?"-":train.platform);
    }

    public String estimatedTime() {
        return train.estimatedTime;
    }

    public String id() {
        return train.id;
    }

    public boolean isLate() {
        return train.isLate();
    }
}
