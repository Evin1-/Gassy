package com.loopcupcakes.gassy.network;

import com.loopcupcakes.gassy.BuildConfig;
import com.loopcupcakes.gassy.entities.places.PlaceResponse;
import com.loopcupcakes.gassy.entities.places.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by evin on 6/2/16.
 */
public class RetrofitHelper {
    private static final Retrofit mRetrofit;

    private static final String GOOGLE_PLACES_API_KEY;
    private static final String GOOGLE_MAPS_BASE_URL = "https://maps.googleapis.com";
    private static final String PLACE_TYPE_GAS = "gas_station";
    private static final String PLACE_ORDER_BY = "distance";

    static {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(GOOGLE_MAPS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GOOGLE_PLACES_API_KEY = BuildConfig.GOOGLE_API_KEY;
    }

    public Call<PlaceResponse> buildCall(String latitude, String longitude) {
        GooglePlacesService duckService = mRetrofit.create(GooglePlacesService.class);
        return duckService.listPlaces(buildLocation(latitude, longitude),
                PLACE_TYPE_GAS,
                PLACE_ORDER_BY,
                GOOGLE_PLACES_API_KEY);
    }

    private String buildLocation(String latitude, String longitude) {
        return latitude + "," + longitude;
    }

    public List<Result> getCloseLocations(String latitude, String longitude) {
        ArrayList<Result> results = new ArrayList<>();

        GooglePlacesService duckService = mRetrofit.create(GooglePlacesService.class);

        Call<PlaceResponse> placeResponseCall = duckService.listPlaces(buildLocation(latitude, longitude),
                PLACE_TYPE_GAS,
                PLACE_ORDER_BY,
                GOOGLE_PLACES_API_KEY);

        try {
            results = (ArrayList<Result>) placeResponseCall.execute().body().getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }
}
