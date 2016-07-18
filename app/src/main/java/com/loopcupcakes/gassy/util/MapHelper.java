package com.loopcupcakes.gassy.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by evin on 7/17/16.
 */
public class MapHelper {
    public static void showMap(Context context, Double latitude, Double longitude, String name) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(buildMapUri(latitude, longitude, name));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    private static Uri buildMapUri(Double latitude, Double longitude, String name) {
        return Uri.parse("geo:" + latitude + "," + longitude
                + "?q=" + latitude + "," + longitude
                + "(" + name + ")");
    }
}
