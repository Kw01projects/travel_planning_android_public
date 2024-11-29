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

public class TravelspotListActivity extends AppCompatActivity {

    private final List<String> travelList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelspot_list);

        ListView listView = findViewById(R.id.travelspot_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, travelList);
        listView.setAdapter(adapter);

        // Load travel list from SharedPreferences
        loadTravelListFromPreferences();
    }

    /**
     * SharedPreferences에서 저장된 여행지 목록을 로드
     */
    private void loadTravelListFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("TravelPlanPrefs", MODE_PRIVATE);
        Set<String> travelSet = sharedPreferences.getStringSet("TravelList", new HashSet<>());

        if (travelSet != null) {
            travelList.clear();
            travelList.addAll(travelSet);
            adapter.notifyDataSetChanged();
        }
    }
}