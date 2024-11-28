package com.example.travel_plan.screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.graphics.drawable.ColorDrawable;

import com.example.travel_plan.R;
import com.example.travel_plan.entities.Task;
import com.example.travel_plan.repositories.RepositoryFactory;
import com.example.travel_plan.repositories.TaskRepository;
import com.example.travel_plan.screens.week.WeeklyScheduleAdapter;
import com.example.travel_plan.screens.week.WeeklyScheduleItem;
import com.example.travel_plan.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeeklyScheduleActivity extends AppCompatActivity {

    private RecyclerView weeklyScheduleRecyclerView;
    private WeeklyScheduleAdapter weeklyScheduleAdapter;
    private List<WeeklyScheduleItem> weeklyScheduleList;
    private TaskRepository taskRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule);

        taskRepository = RepositoryFactory.getTaskRepository(this);
        // RecyclerView 초기화
        weeklyScheduleRecyclerView = findViewById(R.id.weekly_schedule_recyclerview);

        // 그리드 레이아웃: 7열 (일요일~토요일), 24시간
        weeklyScheduleRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));

        // 데이터 로드
        loadWeeklySchedule();

        // 어댑터 설정
        weeklyScheduleAdapter = new WeeklyScheduleAdapter(weeklyScheduleList);
        weeklyScheduleRecyclerView.setAdapter(weeklyScheduleAdapter);

        // 구분선 추가 (가로선)
        DividerItemDecoration horizontalDivider = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        horizontalDivider.setDrawable(new ColorDrawable(getResources().getColor(android.R.color.darker_gray)));
        weeklyScheduleRecyclerView.addItemDecoration(horizontalDivider);
    }

    private void loadWeeklySchedule() {
        weeklyScheduleList = new ArrayList<>();
        // 24시간 x 7일 그리드 생성
        int i = 0;
        for (int hour = 0; hour < 24; hour++) {
            for (int day = 0; day < 7; day++) {
                weeklyScheduleList.add(new WeeklyScheduleItem(day, hour, "")); // 기본 빈 데이터
            }
        }

        Date weekStartDate = DateUtils.getWeekStartDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekStartDate);

        for (int day = 1; day < 7; day++) {
            List<Task> tasks = taskRepository.getTaskListByDate(weekStartDate);
            if (tasks.size() > 0)
                for (Task task : tasks)
                    weeklyScheduleList.get(getHourIndex(day, task.getStartHour())).setTask(task.getTitle());
            calendar.add(Calendar.DATE, 1);
            weekStartDate = calendar.getTime();
        }

        // 예시 일정 데이터 추가 (실제 데이터는 ScheduleActivity에서 전달받아야 함)
//        weeklyScheduleList.get(34).setTask("플랜 2");    // 화요일 오후 10시
    }

    private int getHourIndex(int colIndex, int startHour) {
        if (startHour == 0) return colIndex;
        for (int i = 0; i < startHour; i++) colIndex += 7;
        return colIndex;
    }
}