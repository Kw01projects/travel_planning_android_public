package com.example.travel_plan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.travel_plan.entities.Travel;
import com.example.travel_plan.repositories.TravelRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
        TravelRepository repo = new TravelRepository(this);

//        Travel travel = new Travel();
//        travel.setTitle("Test 1");
//        travel.setContent("desc...");
//        repo.save(travel);

            repo.findById(4l);
            repo.findById(5l);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
}