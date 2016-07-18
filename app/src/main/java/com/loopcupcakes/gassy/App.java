package com.loopcupcakes.gassy;

import android.app.Application;

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
