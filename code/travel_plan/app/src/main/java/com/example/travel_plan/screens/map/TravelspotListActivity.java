package com.example.travel_plan.screens.map;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_plan.R;
import com.example.travel_plan.entities.MapPlace;
import com.example.travel_plan.repositories.MapPlaceRepository;
import com.example.travel_plan.repositories.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class TravelspotListActivity extends AppCompatActivity {

    private final List<MapPlace> travelList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private MapPlaceRepository mapPlaceRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelspot_list);
        mapPlaceRepository = RepositoryFactory.getMapPlaceRepository(this);

        // Load travel list from SharedPreferences
        loadTravelPlaces();

        ListView listView = findViewById(R.id.travelspot_list);
        List<String> addresses = new ArrayList<>();
        for (MapPlace mapPlace : travelList)
            addresses.add(mapPlace.getAddress());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addresses);
        listView.setAdapter(adapter);
    }

    private void loadTravelPlaces() {
        for (MapPlace mapPlace : mapPlaceRepository.list("TRAVEL"))
            travelList.add(mapPlace);
    }

}