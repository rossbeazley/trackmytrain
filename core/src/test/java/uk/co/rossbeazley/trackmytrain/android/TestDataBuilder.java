package uk.co.rossbeazley.trackmytrain.android;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.departures.Stations;

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

    public static TMTBuilder TMTBuilder() {
        return new TMTBuilder()
                .with(new NetworkClient() {
                    @Override
                    public void get(Request request, Response response) {

                    }
                })
                .with(new NarrowScheduledExecutorService() {
                    @Override
                    public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
                        return null;
                    }
                });
    }

    public static Station anyStation() {
        List<Station> list = new Stations().list();
        return list.get((int) (Math.random()*list.size()));
    }

    public static Direction anyDirection() {
        return Direction.to(anyStation());
    }

    public static String anyTrainsJson() {
        return jsonForTrains((Train[]) anyTrains().toArray());
    }

    public static Train anyTrain() {
        return new Train(generateId(), generateEstimatedTime(), generateScheduledTime(), generatePlatform(), false);
    }

    private static String generatePlatform() {
        return String.valueOf((int)Math.random()*10);
    }

    private static String generateEstimatedTime() {
        return Math.random()>0.5?"On time":generateScheduledTime();
    }

    private static String generateScheduledTime() {
        int hour = (int) (Math.random()*24);
        int minute = (int) (Math.random()*60);
        return hour + ":" + minute;
    }

    private static String generateId() {
        StringBuilder sb = new StringBuilder();
        for(int i=0 ; i<25 ; i++) {
            int choice = (int) (Math.random()*3);
            switch(choice) {
                case 0:
                    sb.append(randomCharacter_A_to_Z());
                    break;
                case 1:
                    sb.append(randomCharacter_a_to_z());
                    break;
                case 2:
                    sb.append(randSingleDigitInt());
                    break;
                default:
                    break;
            }
        }
        sb.append("==");
        return sb.toString();
    }

    private static String randomCharacter_a_to_z() {
        char baseChar = 'a';
        return randomChar(baseChar);
    }

    private static String randomCharacter_A_to_Z() {
        char baseChar = 'A';
        return randomChar(baseChar);
    }

    private static String randomChar(char baseChar) {
        int offset = (int) (Math.random()*26);
        return String.valueOf(Character.toChars(baseChar + offset));
    }

    private static int randSingleDigitInt() {
        return (int) (Math.random()*10);
    }

    public static List<Train> anyTrains() {
        final Train train1, train2;
        train1 = anyTrain();
        train2 = anyTrain();
        return Arrays.asList(train1,train2);
    }


    public static TrainViewModel anyTrainViewModel() {
        return new TrainViewModel(anyTrain());
    }

}
