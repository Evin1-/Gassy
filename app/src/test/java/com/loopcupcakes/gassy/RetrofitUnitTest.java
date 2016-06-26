package com.loopcupcakes.gassy;

import com.loopcupcakes.gassy.entities.places.Result;
import com.loopcupcakes.gassy.network.RetrofitHelper;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by evin on 6/2/16.
 */
public class RetrofitUnitTest {
    private static final String LATITUDE_TEST = "19.3352665";
    private static final String LONGITUDE_TEST = "-99.172322";

    @Test
    public void http_getsData() throws Exception {
        RetrofitHelper retrofitHelper = new RetrofitHelper();

        List<Result> results = retrofitHelper.getCloseLocations(LATITUDE_TEST, LONGITUDE_TEST);

        assertNotNull(results);
        assertTrue(results.size() > 0);

        for (Result result : results) {
            System.out.println(result);
        }

        assertEquals(4, 2 + 2);
    }
}
