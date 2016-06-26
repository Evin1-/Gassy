package com.loopcupcakes.gassy.network;

import com.loopcupcakes.gassy.entities.places.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by evin on 6/2/16.
 */
public interface GooglePlacesService {
    @GET("/maps/api/place/nearbysearch/json")
    Call<PlaceResponse> listPlaces(@Query("location") String location,
                                   @Query("type") String type,
                                   @Query("rankby") String rankby,
                                   @Query("key") String key);
}
