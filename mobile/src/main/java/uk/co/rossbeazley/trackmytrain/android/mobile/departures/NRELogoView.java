package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.view.ViewGroup;

import uk.co.rossbeazley.trackmytrain.android.CanPresentDepartureQueries;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;

class NRELogoView implements DeparturesView {

    private final FindsView findsView;
    private final CanPresentDepartureQueries departuresPresenter;
    private View nreLogo;

    public NRELogoView(FindsView findsView, CanPresentDepartureQueries departuresPresenter) {
        this.findsView = findsView;
        nreLogo = findsView.findViewById(R.id.powered_by_nre);
        this.departuresPresenter = departuresPresenter;
    }

    @Override
    public void present(DeparturesViewModel trains) {

    }

    @Override
    public void loading() {
        final ViewGroup viewById = (ViewGroup) (findsView.findViewById(R.id.servicedetails));
        viewById.removeView(nreLogo);
        viewById.removeView(findsView.findViewById(R.id.powered_by_nre_spacer));
        departuresPresenter.detach(this);
    }

    @Override
    public void error(TMTError error) {

    }

}
