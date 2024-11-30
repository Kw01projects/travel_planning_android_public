package com.example.travel_plan.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_plan.R;
import com.example.travel_plan.entities.MapPlace;
import com.example.travel_plan.repositories.MapPlaceRepository;
import com.example.travel_plan.repositories.RepositoryFactory;
import com.example.travel_plan.screens.map.SavedPlacesActivity;
import com.example.travel_plan.screens.map.TravelspotListActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng selectedLocation;
    private Marker preMarker; // flag to remove prev marker

    private MapPlaceRepository mapPlaceRepository;
    private List<MapPlace> savedMapPlaces;
    private Map<String, Boolean> savedMapPlacesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapPlaceRepository = RepositoryFactory.getMapPlaceRepository(this);
        // 지도 초기화
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // 메뉴 버튼 초기화
        ImageView menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(this::showPopupMenu);

        // 하단 버튼 초기화
        Button btnSaveToTravelList = findViewById(R.id.btn_save_to_travel_list);
        Button btnSaveToPlaces = findViewById(R.id.btn_save_to_places);

        // 여행지 저장 버튼 클릭 리스너
        btnSaveToTravelList.setOnClickListener(v -> {
            if (selectedLocation != null) {
                String locationName = getAddressFromLatLng(selectedLocation);
                if (locationName != null) {
                    this.checkAndSaveMapPlace("TRAVEL", locationName, selectedLocation);

                    saveToPreferences("TravelList", locationName);
                    Toast.makeText(this, "여행지 리스트에 저장되었습니다!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "주소 변환에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "지도를 클릭하여 위치를 선택하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        // 저장한 장소 저장 버튼 클릭 리스너
        btnSaveToPlaces.setOnClickListener(v -> {
            if (selectedLocation != null) {
                String locationName = getAddressFromLatLng(selectedLocation);

                if (locationName != null) {
                    this.checkAndSaveMapPlace("NORMAL", locationName, selectedLocation);
                    saveToPreferences("SavedPlaces", locationName);
                    Toast.makeText(this, "저장한 장소 리스트에 저장되었습니다!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "주소 변환에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "지도를 클릭하여 위치를 선택하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

//        try {
//            // current gps location is incorrect
//            GPSTrackerService tracker = new GPSTrackerService(this);
//            if (tracker.canGetLocation()) {
//                LatLng seoul = new LatLng(tracker.getLatitude(), tracker.getLongitude());
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 15));
//            } else {
//                tracker.showSettingsAlert();
//            }
//        } catch (Exception ex) {
//            System.out.println("Cannot set current location");
//        }
        LatLng seoul = new LatLng(37.6194, 127.0598);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 15));

        // 지도 클릭 리스너
        mMap.setOnMapClickListener(latLng -> {
            // if prev marker was saved, don't remove it
            if (preMarker != null && !this.savedMapPlacesMap.containsKey(
                    preMarker.getPosition().latitude +
                            String.valueOf(preMarker.getPosition().latitude)
            )) preMarker.remove();

            selectedLocation = latLng;
            preMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("선택된 위치"));
        });
        init();
    }

    private void init() {
        savedMapPlaces = new ArrayList<>();
        savedMapPlacesMap = new HashMap<>();
        savedMapPlaces.addAll(mapPlaceRepository.list("TRAVEL"));
        savedMapPlaces.addAll(mapPlaceRepository.list("NORMAL"));
        for (MapPlace mapPlace : savedMapPlaces)
            this.addMarker(mapPlace);
    }

    private void addMarker(MapPlace mapPlace) {
        this.savedMapPlacesMap.put(mapPlace.getLatitude() + mapPlace.getLongitude(), true);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(mapPlace.getLatitude()), Double.parseDouble(mapPlace.getLongitude())))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title(""));
    }

    private void checkAndSaveMapPlace(String travel, String locationName, LatLng selectedLocation) {
        if (mapPlaceRepository.findByAddress(travel, locationName) != null) {
            Toast.makeText(this, "주소는 이미 저정되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        MapPlace mapPlace = new MapPlace();
        mapPlace.setAddress(locationName);
        mapPlace.setLatitude(String.valueOf(selectedLocation.latitude));
        mapPlace.setLongitude(String.valueOf(selectedLocation.longitude));
        mapPlace.setType(travel);
        mapPlaceRepository.save(mapPlace);
        addMarker(mapPlace);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.maps_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_travel_list) {
                startActivity(new Intent(this, TravelspotListActivity.class));
                return true;
            } else if (item.getItemId() == R.id.action_saved_places) {
                startActivity(new Intent(this, SavedPlacesActivity.class));
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    /**
     * 좌표를 주소로 변환
     */
    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0).getAddressLine(0); // 전체 주소 반환
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SharedPreferences에 데이터 저장
     */
    private void saveToPreferences(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("TravelPlanPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> dataSet = sharedPreferences.getStringSet(key, new HashSet<>());
        dataSet.add(value);

        editor.putStringSet(key, dataSet);
        editor.apply();
    }
}
