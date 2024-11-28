package com.example.travel_plan.screens;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_plan.R;
import com.example.travel_plan.dtos.ScheduleItem;
import com.example.travel_plan.entities.Task;
import com.example.travel_plan.entities.Travel;
import com.example.travel_plan.repositories.RepositoryFactory;
import com.example.travel_plan.repositories.TaskRepository;
import com.example.travel_plan.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodayScheduleActivity extends AppCompatActivity {

    private EditText scheduleInput, memoInput;
    private Button timePickerButton, addScheduleButton;
    private RecyclerView scheduleRecyclerView;
    private TextView selectedDateText;

    private String selectedStartTime = "";
    private String selectedEndTime = "";
    private List<ScheduleItem> scheduleList;
    private TodayScheduleAdapter scheduleAdapter;
    private Date parsedSelectedDate;
    private Button closeBtn;
    private Button saveBtn;
    private TaskRepository taskRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_schedule);

        // UI 요소 초기화
        scheduleInput = findViewById(R.id.schedule_input);
        memoInput = findViewById(R.id.memo_input);
        timePickerButton = findViewById(R.id.time_picker_button);
        addScheduleButton = findViewById(R.id.add_schedule_button);
        scheduleRecyclerView = findViewById(R.id.schedule_recyclerview);
        selectedDateText = findViewById(R.id.selected_date_text);
        closeBtn = findViewById(R.id.closeBtn);
        saveBtn = findViewById(R.id.saveBtn);
        taskRepository = RepositoryFactory.getTaskRepository(this);

        // RecyclerView 초기화
        scheduleList = new ArrayList<>();
        scheduleAdapter = new TodayScheduleAdapter(scheduleList);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleRecyclerView.setAdapter(scheduleAdapter);

        // MainActivity2에서 전달된 날짜 가져오기
        String selectedDate = getIntent().getStringExtra("selectedDate");
        parsedSelectedDate = DateUtils.parseDbDate(selectedDate.replace(".", "-"));
        if (selectedDate != null) {
            selectedDateText.setText("선택된 날짜: " + selectedDate);
        }

        // 시간 선택 버튼 이벤트
        timePickerButton.setOnClickListener(v -> showTimePickerDialog());

        // 일정 추가 버튼 이벤트
        addScheduleButton.setOnClickListener(v -> addSchedule());

        closeBtn.setOnClickListener(v -> this.finish());
        saveBtn.setOnClickListener(v -> {
            if (scheduleList.size() == 0) {
                Toast.makeText(this, "일정을 입력하세요", Toast.LENGTH_SHORT).show();
            } else {
                for (ScheduleItem item : scheduleList)
                    taskRepository.save(item.getTaskInstance());
                Travel travel = new Travel();
                travel.setStartDate(parsedSelectedDate);
                RepositoryFactory.getTravelRepository(this).save(travel);
                this.finish();
            }
        });

        showMyTaskList();
    }

    private void showMyTaskList() {
        List<Task> tasks = taskRepository.getTaskListByDate(parsedSelectedDate);

        System.out.println("my task list size: " + tasks.size());
        for (Task t : tasks)
            scheduleList.add(new ScheduleItem(t));
        Travel travel = RepositoryFactory.getTravelRepository(this).findByDate(parsedSelectedDate);
        if (travel != null)
            memoInput.setText(travel.getMemo());
    }

    private void showTimePickerDialog() {
        TimePickerDialog startTimePicker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            selectedStartTime = String.format("%02d:%02d", hourOfDay, minute);

            TimePickerDialog endTimePicker = new TimePickerDialog(this, (view1, endHour, endMinute) -> {
                selectedEndTime = String.format("%02d:%02d", endHour, endMinute);
                Toast.makeText(this, "시간 설정: " + selectedStartTime + " - " + selectedEndTime, Toast.LENGTH_SHORT).show();
                timePickerButton.setText(selectedStartTime + "-" + selectedEndTime);
            }, 12, 0, true);
            endTimePicker.setTitle("종료 시간 선택");
            endTimePicker.show();
        }, 12, 0, true);
        startTimePicker.setTitle("시작 시간 선택");
        startTimePicker.show();
    }

    private void addSchedule() {
        String scheduleText = scheduleInput.getText().toString().trim();
        if (scheduleText.isEmpty() || selectedStartTime.isEmpty() || selectedEndTime.isEmpty()) {
            Toast.makeText(this, "일정과 시간을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        ScheduleItem newItem = new ScheduleItem(scheduleText, selectedStartTime, selectedEndTime, false, parsedSelectedDate);
        scheduleList.add(newItem);
        scheduleAdapter.notifyDataSetChanged();

        scheduleInput.setText("");
        selectedStartTime = "";
        selectedEndTime = "";
        timePickerButton.setText("시간 선택");
    }
}