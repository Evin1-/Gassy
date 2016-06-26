package com.loopcupcakes.gassy.entities.firebase;

import java.util.Map;

/**
 * Created by evin on 6/20/16.
 */
public class Station {

    public double latitude;
    public double longitude;
    public double rating;
    public boolean isValid;

    public Station() {

    }

    public Station(double latitude, double longitude, double rating, boolean isValid) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.isValid = isValid;
    }
}
