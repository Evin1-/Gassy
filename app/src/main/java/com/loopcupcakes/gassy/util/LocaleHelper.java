package com.loopcupcakes.gassy.util;

import android.content.Context;

import java.util.Locale;

/**
 * Created by evin on 7/17/16.
 */
public class LocaleHelper {
    public static Locale getCurrentLocale(Context context) {
        return context.getResources().getConfiguration().locale;
    }
}
