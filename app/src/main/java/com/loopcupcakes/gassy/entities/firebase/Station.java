package com.loopcupcakes.gassy.entities.firebase;

/**
 * Created by evin on 6/20/16.
 */
public class Station {

    public String id;
    public String name;
    public String vicinity;
    public double latitude;
    public double longitude;
    public double rating;
    public boolean valid;

    public Station() {

    }

    public Station(String id, String name, String vicinity, double latitude, double longitude, double rating, boolean valid) {
        this.id = id;
        this.name = name;
        this.vicinity = vicinity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.valid = valid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
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
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        return id != null ? id.equals(station.id) : station.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
