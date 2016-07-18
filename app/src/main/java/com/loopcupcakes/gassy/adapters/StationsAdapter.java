package com.loopcupcakes.gassy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopcupcakes.gassy.R;
import com.loopcupcakes.gassy.entities.firebase.Station;
import com.loopcupcakes.gassy.util.FormatHelper;
import com.loopcupcakes.gassy.util.MapHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by evin on 6/3/16.
 */
public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder> {

    private static final String TAG = "StationsAdapterTAG_";

    private final ArrayList<Station> mStations;
    private final HashMap<Station, Double> mDistances;
    private final Locale mCurrentLocale;

    public static Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // TODO: 7/17/16 Add Vicinity?

        private final TextView nameTextView;
        private final TextView coordinatesTextView;
        private final TextView distanceTextView;
        private final TextView ratingTextView;

        private String itemName;
        private Double itemLatitude;
        private Double itemLongitude;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.rvItemTitle);
            coordinatesTextView = (TextView) itemView.findViewById(R.id.rvItemPopularity);
            distanceTextView = (TextView) itemView.findViewById(R.id.rvItemShortDescription);
            ratingTextView = (TextView) itemView.findViewById(R.id.rvItemRating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MapHelper.showMap(mContext, itemLatitude, itemLongitude, itemName);
                }
            });
        }
    }

    public StationsAdapter(ArrayList<Station> stations, HashMap<Station, Double> distances, Locale locale, Context context) {
        this.mStations = stations;
        this.mDistances = distances;
        this.mCurrentLocale = locale;
        mContext = context;
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
        textViewName.setText(FormatHelper.formatTitle(station.getName()));

        TextView textViewCoordinates = holder.coordinatesTextView;
        textViewCoordinates.setText(String.format(mCurrentLocale, "%.5f %.5f", station.getLatitude(), station.getLongitude()));

        TextView textViewDistance = holder.distanceTextView;
        textViewDistance.setText(String.format(mCurrentLocale, "%.2f", mDistances.get(station)));

        TextView textViewRating = holder.ratingTextView;
        textViewRating.setText(String.format(mCurrentLocale, "%.2f", station.getRating()));

        holder.itemLatitude = station.getLatitude();
        holder.itemLongitude = station.getLongitude();
        holder.itemName = station.getName();
    }

    @Override
    public int getItemCount() {
        return (mStations == null) ? 0 : mStations.size();
    }
}
