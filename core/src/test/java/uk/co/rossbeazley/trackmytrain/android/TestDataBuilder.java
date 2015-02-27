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

    public static String jsonForTrains(Train... trains) {
        String SEP = ",\n";

        String header = "{\n" +
                "\"error\": \"\",\n" +
                "\"trains\": [\n";
        String tail = "]\n" +
                "}";

        String body = "";

        for(Train train : trains) {
            body += TestDataBuilder.jsonForTrain(train) + SEP;
        }

        if(trains.length>1) {
            body = body.substring(0,body.length()-2);
        }

        return header +
                body +
                tail;
    }
}
