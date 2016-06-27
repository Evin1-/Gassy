package com.loopcupcakes.gassy.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopcupcakes.gassy.R;
import com.loopcupcakes.gassy.adapters.StationsAdapter;
import com.loopcupcakes.gassy.entities.firebase.Station;
import com.loopcupcakes.gassy.entities.places.Location;
import com.loopcupcakes.gassy.entities.places.PlaceResponse;
import com.loopcupcakes.gassy.entities.places.Result;
import com.loopcupcakes.gassy.network.RetrofitHelper;
import com.loopcupcakes.gassy.util.NetworkChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationsFragment extends Fragment {

    private static final String TAG = "StationsFragmentTAG_";

    private static final String STATIONS_CHILD_KEY = "stations";

    private RecyclerView mRecyclerView;
    private ArrayList<Station> mStations;
    private HashMap<Station, Double> mDistances;
    private StationsAdapter mAdapter;

    private DatabaseReference mDatabase;

    private static final String LATITUDE_TEST = "19.3352665";
    private static final String LONGITUDE_TEST = "-99.172322";

    public StationsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        mStations = new ArrayList<>();
        mDistances = new HashMap<>();
        mAdapter = new StationsAdapter(mStations);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stations, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.f_stations_recycler);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        refreshRecycler();
    }

    private void refreshRecycler() {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        Call<PlaceResponse> placeResponseCall = retrofitHelper.buildCall(LATITUDE_TEST, LONGITUDE_TEST);
        if (NetworkChecker.checkInternet(getActivity().getApplicationContext()) != NetworkChecker.NO_CONNECTION) {
            placeResponseCall.enqueue(new Callback<PlaceResponse>() {
                @Override
                public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                    List<Result> results = response.body().getResults();

                    for (Result result : results) {
                        Log.d(TAG, "onResponse: " + result.getGeometry().getLocation() + " " + result.getName());
                        pushStation(result);
                    }
                }

                @Override
                public void onFailure(Call<PlaceResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t);
                }
            });
        } else {
            // TODO: 6/3/16 Print SnackBar in correct view
            Snackbar.make(mRecyclerView, "No Internet connection", Snackbar.LENGTH_LONG).show();
        }
    }

    private void pushStation(Result result) {
        final Location location = result.getGeometry().getLocation();
        final Double latitude = location.getLat();
        final Double longitude = location.getLng();
        final String placeId = result.getPlaceId();
        final String name = result.getName();
        final String vicinity = result.getVicinity();
        final String id = result.getId();

        // TODO: 6/26/16 Do everything in a transaction
        // TODO: 6/26/16 Add distance as second parameter

        final DatabaseReference stationReference = mDatabase.child(STATIONS_CHILD_KEY).child(placeId);
        stationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Station station = dataSnapshot.getValue(Station.class);
                if (station == null) {
                    station = new Station(id, name, vicinity, latitude, longitude, 0.0, true);
                    stationReference.setValue(station);
                } else {
                    Log.d(TAG, "onDataChange: " + station.getLatitude() + " " + station.getLongitude());
                }

                if (!mDistances.containsKey(station)) {
                    mStations.add(station);
                    mDistances.put(station, 0.0);
                    mAdapter.notifyDataSetChanged();
                }
                Log.d(TAG, "onDataChange: " + station.hashCode() + " " + mStations.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError);
            }
        });
    }
}
