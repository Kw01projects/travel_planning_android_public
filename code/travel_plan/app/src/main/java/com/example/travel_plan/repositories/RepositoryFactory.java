package com.example.travel_plan.repositories;


import android.content.Context;

// impl singleton pattern for repositories
public class RepositoryFactory {
    private static UserRepository userRepository;
    private static TravelRepository travelRepository;
    private static TaskRepository taskRepository;
    private static PlaceRepository placeRepository;
    private static MapPlaceRepository mapPlaceRepository;
    private static MoneyLedgerRepository moneyLedgerRepository;

    public static void init(Context context) {
        getUserRepository(context);
        getTravelRepository(context);
        getTaskRepository(context);
        getPlaceRepository(context);
        getMapPlaceRepository(context);
        getMoneyLedgerRepository(context);
    }

    public static UserRepository getUserRepository(Context context) {
        if (userRepository == null)
            userRepository = new UserRepository(context.getApplicationContext());
        return userRepository;
    }

    public static TravelRepository getTravelRepository(Context context) {
        if (travelRepository == null)
            travelRepository = new TravelRepository(context.getApplicationContext());
        return travelRepository;
    }

    public static TaskRepository getTaskRepository(Context context) {
        if (taskRepository == null)
            taskRepository = new TaskRepository(context.getApplicationContext());
        return taskRepository;
    }

    public static PlaceRepository getPlaceRepository(Context context) {
        if (placeRepository == null)
            placeRepository = new PlaceRepository(context.getApplicationContext());
        return placeRepository;
    }

    public static MapPlaceRepository getMapPlaceRepository(Context context) {
        if (mapPlaceRepository == null)
            mapPlaceRepository = new MapPlaceRepository(context.getApplicationContext());
        return mapPlaceRepository;
    }

    public static MoneyLedgerRepository getMoneyLedgerRepository(Context context) {
        if (moneyLedgerRepository == null)
            moneyLedgerRepository = new MoneyLedgerRepository(context.getApplicationContext());
        return moneyLedgerRepository;
    }
}
