package com.loopcupcakes.gassy.entities.firebase;

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
