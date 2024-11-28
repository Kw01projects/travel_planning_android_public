package com.example.travel_plan.repositories;


import android.content.Context;

// impl singleton pattern for repositories
public class RepositoryFactory {
    private static UserRepository userRepository;
    private static TravelRepository travelRepository;
    private static TaskRepository taskRepository;

    public static void init(Context context){
        getUserRepository(context);
        getTravelRepository(context);
        getTaskRepository(context);
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
}
