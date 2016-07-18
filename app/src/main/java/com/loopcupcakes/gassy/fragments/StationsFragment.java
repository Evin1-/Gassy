package com.loopcupcakes.gassy.fragments;


import android.location.Location;
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
import com.loopcupcakes.gassy.entities.event.LocationEvent;
import com.loopcupcakes.gassy.entities.firebase.Station;
import com.loopcupcakes.gassy.entities.places.Loc;
import com.loopcupcakes.gassy.entities.places.PlaceResponse;
import com.loopcupcakes.gassy.entities.places.Result;
import com.loopcupcakes.gassy.network.RetrofitHelper;
import com.loopcupcakes.gassy.util.LocaleHelper;
import com.loopcupcakes.gassy.util.LocationHelper;
import com.loopcupcakes.gassy.util.NetworkChecker;
import com.loopcupcakes.gassy.util.RandomHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationsFragment extends Fragment {

    private static final String TAG = "StationsFragmentTAG_";

    private static final String STATIONS_CHILD_KEY = "stations";
    private static final String ORDERED_BY_RATING_KEY = "ORDERED_BY_RATING";
    private static final Double LATITUDE_DEFAULT = 19.4326018;
    private static final Double LONGITUDE_DEFAULT = -99.1353936;

    private RecyclerView mRecyclerView;
    private ArrayList<Station> mStations;
    private HashMap<Station, Double> mDistances;
    private StationsAdapter mAdapter;

    private Location mCurrentLocation;

    private DatabaseReference mDatabase;

    private boolean mOrderedByRating;

    public StationsFragment() {

    }

    public static StationsFragment newInstance(Boolean orderedByRating) {
        Bundle args = new Bundle();
        args.putBoolean(ORDERED_BY_RATING_KEY, orderedByRating);

        StationsFragment fragment = new StationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mStations = new ArrayList<>();
        mDistances = new HashMap<>();
        mAdapter = new StationsAdapter(mStations, mDistances, LocaleHelper.getCurrentLocale(getContext()));
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setupCurrentLocation();

        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        mOrderedByRating = args.getBoolean(ORDERED_BY_RATING_KEY, false);
        return inflater.inflate(R.layout.fragment_stations, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.f_stations_recycler);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        refreshRecycler(new LocationEvent(mCurrentLocation));
        Log.d(TAG, "onViewCreated: ");
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void refreshRecycler(LocationEvent locationEvent) {
        Log.d(TAG, "refreshRecycler: " + getTag() + " " + locationEvent.getLatitude() + " " + locationEvent.getLongitude());
        mCurrentLocation = locationEvent.getLocation();
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        Call<PlaceResponse> placeResponseCall = retrofitHelper.buildCall(locationEvent.getLatitude(), locationEvent.getLongitude());
        if (NetworkChecker.checkInternet(getContext()) != NetworkChecker.NO_CONNECTION) {
            placeResponseCall.enqueue(new Callback<PlaceResponse>() {
                @Override
                public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                    List<Result> results = response.body().getResults();

                    for (Result result : results) {
//                        Log.d(TAG, "onResponse: " + result.getGeometry().getLoc() + " " + result.getName() + " " + result.getPhotos().size());
                        pushStation(result);
                    }

                    for (Map.Entry<Station, Double> entry : mDistances.entrySet()) {
                        Log.d(TAG, "onResponse: " + entry.getKey() + " " + entry.getValue());
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
        final Loc loc = result.getGeometry().getLoc();
        final Double latitude = loc.getLat();
        final Double longitude = loc.getLng();
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
                    // TODO: 7/17/16 Change rating to query actual one
                    station = new Station(id, name, vicinity, latitude, longitude, RandomHelper.generateRandom(5), true);
                    stationReference.setValue(station);
                } else {
//                    Log.d(TAG, "onDataChange: " + station.getLatitude() + " " + station.getLongitude());
                }

                if (!mDistances.containsKey(station)) {
                    // TODO: 7/14/16 Check if it is far enough from previous
                    mStations.add(station);
                    Location aux = buildLocation(station.getLatitude(), station.getLongitude());
                    mDistances.put(station, (double) mCurrentLocation.distanceTo(aux));
                    Log.d(TAG, "onDataChange: " + station + " " + mDistances.get(station) + " " + station.getRating());
                    // TODO: 6/27/16 Change to notifyItemInserted
                    mAdapter.notifyDataSetChanged();
                }
//                Log.d(TAG, "onDataChange: " + station.hashCode() + " " + mStations.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError);
            }
        });
    }

    private void setupCurrentLocation() {
        LocationHelper locationHelper = new LocationHelper(getActivity().getApplicationContext());
        Location lastKnownLocation = locationHelper.getLastLocation();

        if (lastKnownLocation == null) {
            mCurrentLocation = buildLocation(LATITUDE_DEFAULT, LONGITUDE_DEFAULT);
        } else {
            mCurrentLocation = lastKnownLocation;
        }
    }

    private Location buildLocation(Double latitude, Double longitude) {
        Location aux = new Location("");
        aux.setLatitude(latitude);
        aux.setLongitude(longitude);

        return aux;
    }
}
