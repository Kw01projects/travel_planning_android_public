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

public class SavedPlacesActivity extends AppCompatActivity {

    private final List<MapPlace> savedPlacesList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private MapPlaceRepository mapPlaceRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_places);

        mapPlaceRepository = RepositoryFactory.getMapPlaceRepository(this);
        ListView listView = findViewById(R.id.saved_places_list);
        loadTravelPlaces();
        List<String> addresses = new ArrayList<>();
        for (MapPlace mapPlace : savedPlacesList)
            addresses.add(mapPlace.getAddress());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, addresses);
        listView.setAdapter(adapter);
    }

    private void loadTravelPlaces() {
        for (MapPlace mapPlace : mapPlaceRepository.list("NORMAL"))
            savedPlacesList.add(mapPlace);
    }
}