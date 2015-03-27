package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

public interface DeparturesView {
    void present(DeparturesViewModel trains);

    void loading();

    void error(String error);
}
