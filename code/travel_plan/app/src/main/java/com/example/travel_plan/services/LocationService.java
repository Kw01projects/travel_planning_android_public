package com.example.travel_plan.services;

import android.location.LocationManager;

public class LocationService {
    private final LocationManager locationManager;

    public LocationService(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public String[] getCurrentLocation() {
        return null;
    }
}
