package uk.co.rossbeazley.trackmytrain.android;

public class Train {
    public final String eta;
    public final String std;
    public final String id;

    public Train(String id, String eta, String std)
    {
        this.id = id;
        this.eta = eta;
        this.std = std;
    }

    public String toString() {
        return eta + " : " + std + " : " + id + System.getProperty("line.separator");
    }

}
