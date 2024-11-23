package com.example.travel_plan.repositories;

import java.util.Calendar;
import java.util.Date;

public class Test {
    public void test() {
        try {
            TravelRepository repo = new TravelRepository(null);
            TaskRepository taskRepository = new TaskRepository(null);

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
