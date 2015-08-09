package uk.co.rossbeazley.trackmytrain.wsclient;


public class DeparturesClientTest {

    public static void main(String[] args) {

        DeparturesResult result = DeparturesClient.services("MCO", "MAN");
        final String id = result.firstTrain().id;

        while (true) {
            //final String id = "O6QG+R339n8rjeTufcLqYg==";
            System.out.println(ServiceDetailsClient.serviceDetails(id).toJson());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}