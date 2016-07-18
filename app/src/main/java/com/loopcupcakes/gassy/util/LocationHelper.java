package com.loopcupcakes.gassy.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.loopcupcakes.gassy.entities.events.LocationEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by evin on 6/28/16.
 */
public class LocationHelper implements LocationListener {
    private static final String TAG = "GPSHelperTAG_";

    private LocationManager mLocationManager;

    public LocationHelper(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: " + location.getAccuracy());
        if (location.getAccuracy() < 100) {
            EventBus.getDefault().post(new LocationEvent(location));
            stopUpdates();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d(TAG, "onStatusChanged: ");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d(TAG, "onProviderEnabled: ");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d(TAG, "onProviderDisabled: ");
    }

    public void requestUpdates() {
        Log.d(TAG, "requestUpdates: ");
        if (mLocationManager != null) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    public void stopUpdates() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }
    }

    public Location getLastLocation() {
        if (mLocationManager != null) {
            Criteria criteria = new Criteria();
            String bestProvider = mLocationManager.getBestProvider(criteria, false);
            return mLocationManager.getLastKnownLocation(bestProvider);
        }
        return null;
    }
}
