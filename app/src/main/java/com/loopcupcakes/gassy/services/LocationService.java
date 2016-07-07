package com.loopcupcakes.gassy.services;

import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.loopcupcakes.gassy.util.LocationHelper;

/**
 * Created by evin on 7/7/16.
 */
public class LocationService extends Service {

    private LocationHelper mLocationHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        setupLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setupLocation() {
        if (mLocationHelper != null) {
            mLocationHelper.stopUpdates();
        }
        mLocationHelper = new LocationHelper(getApplicationContext());
        mLocationHelper.requestUpdates();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationHelper != null) {
            mLocationHelper.stopUpdates();
        }
    }
}
