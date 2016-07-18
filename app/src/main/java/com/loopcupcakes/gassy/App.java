package com.loopcupcakes.gassy;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by evin on 7/7/16.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
    }
}
