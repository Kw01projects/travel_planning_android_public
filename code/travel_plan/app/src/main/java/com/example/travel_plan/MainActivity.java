package com.example.travel_plan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.travel_plan.entities.Task;
import com.example.travel_plan.repositories.TaskRepository;
import com.example.travel_plan.repositories.TravelRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            TravelRepository repo = new TravelRepository(this);
            TaskRepository taskRepository = new TaskRepository(this);

            Date currentDate = Calendar.getInstance().getTime();
//            Task newTask = new Task();
//            newTask.setTitle("Go to bed");
//            newTask.setStartTime("18:00");
//            newTask.setHasDone(false);
//            newTask.setStartDate(currentDate);
//            newTask =  taskRepository.save(newTask);
////            System.out.println(taskRepository.findById(1L));
//            List<Task> tasks = taskRepository.getTaskListByDate(currentDate);
//            System.out.println("Today task cnt: " + tasks.size());
//            System.out.println(tasks.get(0));
//            System.out.println(tasks.get(1));
//            taskRepository.changeTaskStatus(1L, true); // markTaskDone

//        Travel travel = new Travel();
//        travel.setTitle("Test 1");
//        travel.setContent("desc...");
//        repo.save(travel);
//            repo.findById(4l);
//            repo.findById(5l);

//            for getting user's info
//            UserRepository userRepository = new UserRepository(this);
//            User user = userRepository.getMyInfo();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}