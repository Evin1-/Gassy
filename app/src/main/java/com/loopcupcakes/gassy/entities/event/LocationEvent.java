package com.loopcupcakes.gassy.entities.event;

import android.location.Location;

/**
 * Created by evin on 7/13/16.
 */
public class LocationEvent {
    public Location location;

    public LocationEvent(Location location) {
        this.location = location;
    }

    public Double getLatitude() {
        return location.getLatitude();
    }

    public Double getLongitude() {
        return location.getLongitude();
    }
}
