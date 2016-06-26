package com.loopcupcakes.gassy.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by evin on 6/3/16.
 */
public class NetworkChecker {
    public static final int NO_CONNECTION = 0;
    public static final int CEL_CONNECTED = 1;
    public static final int WIFI_CONNECTED = 2;

    public static int checkInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return WIFI_CONNECTED;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return CEL_CONNECTED;
            }
        }
        return NO_CONNECTION;
    }
}
