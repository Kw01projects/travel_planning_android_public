package com.example.travel_plan.screens.map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_plan.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SavedPlacesActivity extends AppCompatActivity {

    private final List<String> savedPlacesList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_places);

        ListView listView = findViewById(R.id.saved_places_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, savedPlacesList);
        listView.setAdapter(adapter);

        // Load saved places from SharedPreferences
        loadSavedPlacesFromPreferences();
    }

    /**
     * SharedPreferences에서 저장된 장소 목록을 로드
     */
    private void loadSavedPlacesFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("TravelPlanPrefs", MODE_PRIVATE);
        Set<String> savedPlacesSet = sharedPreferences.getStringSet("SavedPlaces", new HashSet<>());

        if (savedPlacesSet != null) {
            savedPlacesList.clear();
            savedPlacesList.addAll(savedPlacesSet);
            adapter.notifyDataSetChanged();
        }
    }
}