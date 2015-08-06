package uk.co.rossbeazley.trackmytrain.wsclient;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class DeparturesResult {

    private final List<Train> trains;
    private final String error;

    DeparturesResult(String error) {
        this.trains = new ArrayList<>();
        this.error = error;
    }

    public static DeparturesResult ok() {
        return new DeparturesResult("");
    }

    public static DeparturesResult error(String error) {
        return new DeparturesResult(error);
    }

    @Override
    public String toString() {
        return trains.toString() + " error?" + error;
    }

    public void add(Train resultRow) {
        trains.add(resultRow);
    }

    public String toJson() {
        String jsonRows = "";
        if (!trains.isEmpty()) {
            for (Train row : trains) {
                jsonRows += row.toJson();
                jsonRows += ",";
            }
            jsonRows = jsonRows.substring(0, jsonRows.length() - 1);
        }
        return "{\n" +
                "\"error\":\"" + error + "\",\n" +
                "\"trains\": [\n" +
                jsonRows +
                "]\n" +
                "}";
    }

}
