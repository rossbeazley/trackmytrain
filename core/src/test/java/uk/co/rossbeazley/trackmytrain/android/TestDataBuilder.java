package uk.co.rossbeazley.trackmytrain.android;

public class TestDataBuilder {
    static public String jsonForTrain(Train trainParam) {
        return "{\n" +
                "\"id\": \"" + trainParam.id + "\",\n" +
                "\"scheduledTime\": \"" + trainParam.scheduledTime + "\",\n" +
                "\"estimatedTime\": \"" + trainParam.estimatedTime + "\",\n" +
                "\"platform\": \"" + trainParam.platform + "\"\n" +
                "}";
    }
}
