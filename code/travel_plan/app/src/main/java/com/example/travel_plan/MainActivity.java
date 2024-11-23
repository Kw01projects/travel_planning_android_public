package com.example.travel_plan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.travel_plan.entities.Task;
import com.example.travel_plan.repositories.TaskRepository;
import com.example.travel_plan.repositories.TravelRepository;
import com.example.travel_plan.services.LocationService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationService locationService= new LocationService((LocationManager) getSystemService(Context.LOCATION_SERVICE));
    }
}