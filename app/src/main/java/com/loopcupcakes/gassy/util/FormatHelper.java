package com.loopcupcakes.gassy.util;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by evin on 7/17/16.
 */
public class FormatHelper {
    public static String formatTitle(String title) {
        return WordUtils.capitalize(WordUtils.capitalize(title.toLowerCase(), '.'))
                .replace("De", "de")
                .replace("Para", "para");
    }
}
