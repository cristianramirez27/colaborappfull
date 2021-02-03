package com.coppel.rhconecta.dev.business.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * Created by jvazquez on 20/02/2017.
 */

public class MyLocation {

    //	private  Context mContext;

    // flag for GPS status
    static boolean isGPSEnabled = false;

    // flag for network status
    static boolean isNetworkEnabled = false;

    // flag for GPS status
    static boolean canGetLocation = false;

    static Location location; // location

    // Declaring a Location Manager
    protected static LocationManager locationManager;

    public static Location getLocation(Context context)  {

        try {
            locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }

        } catch (Exception e) {}

        return location;
    }

    public static boolean isGPSEnabled(Context context) {
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        return  locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public static Location find_Location(Context con) {
        String location_context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) con.getSystemService(location_context);
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            locationManager.requestLocationUpdates(provider, 1000, 0,
                    new LocationListener() {

                        public void onLocationChanged(Location location) {}

                        public void onProviderDisabled(String provider) {}

                        public void onProviderEnabled(String provider) {}

                        public void onStatusChanged(String provider, int status,
                                                    Bundle extras) {}
                    });
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                return location;
            }
        }

        return null;
    }
}