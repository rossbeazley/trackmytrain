package uk.co.rossbeazley.trackmytrain.wsclient;

public class ServiceResult {
    public final Train train;
    public  final String error;

    public ServiceResult(Train result, String error) {

        this.train = result;
        this.error = error;
    }

    public static ServiceResult ok(Train result) {
        return new ServiceResult(result,"");
    }

    public static ServiceResult error(String message) {
        return new ServiceResult(null,message);
    }

    public String toJson() {
        return "{\n" +
                "\"error\":\"" + error + "\",\n" +
                "\"train\":" + train!=null ? train.toJson() : "\"\""+
                "}";

    }
}

