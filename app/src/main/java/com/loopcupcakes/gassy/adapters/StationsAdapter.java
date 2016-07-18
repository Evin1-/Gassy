package com.loopcupcakes.gassy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopcupcakes.gassy.R;
import com.loopcupcakes.gassy.entities.firebase.Station;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by evin on 6/3/16.
 */
public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder> {

    private static final String TAG = "StationsAdapterTAG_";

    private ArrayList<Station> mStations;
    private HashMap<Station, Double> mDistances;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView nameTextView;
        public final TextView coordinatesTextView;
        public final TextView distanceTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.rvItemTitle);
            coordinatesTextView = (TextView) itemView.findViewById(R.id.rvItemPopularity);
            distanceTextView = (TextView) itemView.findViewById(R.id.rvItemShortDescription);
        }
    }

    public StationsAdapter(ArrayList<Station> stations, HashMap<Station, Double> distances) {
        this.mStations = stations;
        this.mDistances = distances;
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

        TextView textViewName = holder.nameTextView;
        textViewName.setText(station.getName());

        TextView textViewCoordinates = holder.coordinatesTextView;
        textViewCoordinates.setText(String.format("%.2f %.2f", station.getLatitude(), station.getLongitude()));

        TextView textViewDistance = holder.distanceTextView;
        textViewDistance.setText(String.format("%.2f", mDistances.get(station)));
    }

    @Override
    public int getItemCount() {
        return (mStations == null) ? 0 : mStations.size();
    }
}
