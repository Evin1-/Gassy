package com.loopcupcakes.gassy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopcupcakes.gassy.R;
import com.loopcupcakes.gassy.entities.firebase.Station;

import java.util.ArrayList;

/**
 * Created by evin on 6/3/16.
 */
public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder> {

    private static final String TAG = "StationsAdapterTAG_";

    private ArrayList<Station> mStations;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.rvItemTitle);
        }
    }

    public StationsAdapter(ArrayList<Station> stations) {
        this.mStations = stations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View gasView = inflater.inflate(R.layout.recycler_station_item, parent, false);

        return new ViewHolder(gasView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Station station = mStations.get(position);

        TextView textViewName = holder.textView;
        textViewName.setText(station.getLatitude() + " " + station.getLongitude());
    }

    @Override
    public int getItemCount() {
        return (mStations == null) ? 0 : mStations.size();
    }
}
