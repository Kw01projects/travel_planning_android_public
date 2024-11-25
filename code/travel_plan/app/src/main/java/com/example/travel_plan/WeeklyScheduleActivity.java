package com.example.travel_plan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;
import android.graphics.drawable.ColorDrawable;

import java.util.ArrayList;
import java.util.List;

public class WeeklyScheduleActivity extends AppCompatActivity {

    private RecyclerView weeklyScheduleRecyclerView;
    private WeeklyScheduleAdapter weeklyScheduleAdapter;
    private List<WeeklyScheduleItem> weeklyScheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule);

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
        for (int hour = 0; hour < 24; hour++) {
            for (int day = 0; day < 7; day++) {
                weeklyScheduleList.add(new WeeklyScheduleItem(day, hour, "")); // 기본 빈 데이터
            }
        }

        // 예시 일정 데이터 추가 (실제 데이터는 ScheduleActivity에서 전달받아야 함)
        weeklyScheduleList.get(8).setTask("호텔 조식");  // 일요일 오전 8시
        weeklyScheduleList.get(17).setTask("플랜 1");    // 월요일 오후 5시
        weeklyScheduleList.get(34).setTask("플랜 2");    // 화요일 오후 10시
    }
}