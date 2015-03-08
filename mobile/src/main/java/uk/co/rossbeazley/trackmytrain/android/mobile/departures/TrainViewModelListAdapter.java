package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

import static uk.co.rossbeazley.trackmytrain.android.mobile.departures.DepartureRow.createDepartureRow;
import static uk.co.rossbeazley.trackmytrain.android.mobile.departures.DepartureRow.recycleDepartureRow;

class TrainViewModelListAdapter extends BaseAdapter {
    private final List<TrainViewModel> trains;

    public TrainViewModelListAdapter(List<TrainViewModel> trains) {
        this.trains = trains;
    }

    @Override
    public int getCount() {
        return trains.size();
    }

    @Override
    public Object getItem(int position) {
        return trains.get(position);
    }

    @Override
    public long getItemId(int position) {
        return trains.get(position).id().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return (convertView == null ? createDepartureRow(parent) : recycleDepartureRow(convertView)).bind(trains.get(position));
    }

}
