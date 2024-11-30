package com.example.travel_plan.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.travel_plan.R;
import com.example.travel_plan.entities.User;
import com.example.travel_plan.repositories.RepositoryFactory;
import com.example.travel_plan.repositories.TravelRepository;
import com.example.travel_plan.repositories.UserRepository;
import com.example.travel_plan.screens.schedule.TodayScheduleActivity;
import com.example.travel_plan.utils.DateUtils;
import com.example.travel_plan.viewModels.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private GridView calendarGrid;
    private Calendar calendar;
    private TextView userNickname;
    private TextView currentMonth;
    private TextView travelScheduleStartDate;
    private TextView travelScheduleEndDate;
    private CalendarAdapter calendarAdapter;

    private UserRepository userRepository;
    private User myInfo;
    private TravelRepository travelRepository;
    private static String currentMonthYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        calendarGrid = findViewById(R.id.calendar_grid);
        currentMonth = findViewById(R.id.current_month);
        travelScheduleStartDate = findViewById(R.id.travel_schedule_start);
        travelScheduleEndDate = findViewById(R.id.travel_schedule_end);
        calendar = Calendar.getInstance();

        setupCalendar();

        ImageView prevMonthButton = findViewById(R.id.prev_month_button);
        prevMonthButton.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1);
            updateCalendar();
        });

        ImageView nextMonthButton = findViewById(R.id.next_month_button);
        nextMonthButton.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, 1);
            updateCalendar();
        });

        // 프로필 이미지 클릭 이벤트
        ImageView profileImage = findViewById(R.id.profile_image);
        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MyPageActivity.class);
            startActivity(intent);
        });

        // 메뉴 버튼 클릭 이벤트
        ImageView menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> showPopupMenu(menuButton));

        // 여행 일정 날짜 선택 이벤트
        travelScheduleStartDate.setOnClickListener(v -> showDatePickerDialog(true));
        travelScheduleEndDate.setOnClickListener(v -> showDatePickerDialog(false));

        // 달력 날짜 클릭 이벤트
        calendarGrid.setOnItemClickListener((parent, view, position, id) -> {
            String selectedDate = getFormattedDate(position);
            if (!selectedDate.isEmpty()) {
                Intent intent = new Intent(HomeActivity.this, TodayScheduleActivity.class);
                intent.putExtra("selectedDate", selectedDate);
                startActivityForResult(intent, 0);
            }
        });

        // 사용자 닉네임 및 여행 일정 데이터 로드
        userRepository = RepositoryFactory.getUserRepository(this);
        userNickname = findViewById(R.id.userNickname);

        myInfo = userRepository.getMyInfo();
        if (myInfo.getStartDate() != null && myInfo.getEndDate() != null) {
            travelScheduleStartDate.setText(DateUtils.formatReadableDate(myInfo.getStartDate()));
            travelScheduleEndDate.setText(DateUtils.formatReadableDate(myInfo.getEndDate()));
        }

        UserViewModel userViewModel = (new ViewModelProvider(this).get(UserViewModel.class));
        userNickname.setText("Hi, ".concat(myInfo.getNickName()));
        userViewModel
                .getUser()
                .observe(this, userState -> {
                    System.out.println("user changed: ");
                    userNickname.setText(userState.getNickName());
                });

        travelRepository = RepositoryFactory.getTravelRepository(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateCalendar();
    }

    private void setupCalendar() {
        updateCalendar();
    }

    private void updateCalendar() {
        List<String> dates = new ArrayList<>();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < firstDayOfWeek; i++) {
            dates.add("");
        }

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInMonth; i++) {
            dates.add(String.valueOf(i));
        }

        this.currentMonthYear = String.valueOf(calendar.get(Calendar.YEAR)).concat("-") + (calendar.get(Calendar.MONTH) + 1);
        calendarAdapter = new CalendarAdapter(dates);
        calendarGrid.setAdapter(calendarAdapter);

        // 현재 월 텍스트 업데이트
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy MMMM", Locale.getDefault());
        currentMonth.setText(monthFormat.format(calendar.getTime()));
    }

    private void showPopupMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> handleMenuItemClick(menuItem));
        popupMenu.show();
    }

    private boolean handleMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_today_schedule:
                String todayDate = getTodayDate();
                Intent intent = new Intent(HomeActivity.this, TodayScheduleActivity.class);
                intent.putExtra("selectedDate", todayDate);
                intent.putExtra("isDetailPage", true);
                startActivity(intent);
                return true;

            case R.id.menu_all_schedules:
                Intent weeklyIntent = new Intent(HomeActivity.this, WeeklyScheduleActivity.class);
                startActivity(weeklyIntent);
                return true;

            case R.id.menu_view_map:
                Intent mapIntent = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(mapIntent);
                return true;

            default:
                return false;
        }
    }

    private String getTodayDate() {
        Calendar today = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return dateFormat.format(today.getTime());
    }

    private String getFormattedDate(int position) {
        Calendar tempCalendar = (Calendar) calendar.clone();
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
        tempCalendar.add(Calendar.DAY_OF_MONTH, position - tempCalendar.get(Calendar.DAY_OF_WEEK) + 1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return dateFormat.format(tempCalendar.getTime());
    }

    private void showDatePickerDialog(boolean isSelectedStartDate) {
        Calendar tempCalendar = Calendar.getInstance();
        int year = tempCalendar.get(Calendar.YEAR);
        int month = tempCalendar.get(Calendar.MONTH);
        int day = tempCalendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // parse date for saving db
            String parsedDateString = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
            Date parsedDate = DateUtils.parseDbDate(parsedDateString);

            if (isSelectedStartDate) {
                travelScheduleStartDate.setText(parsedDateString.replace("-", "."));
                myInfo.setStartDate(parsedDate);
            } else {
                travelScheduleEndDate.setText(parsedDateString.replace("-", "."));
                myInfo.setEndDate(parsedDate);
            }
            if (myInfo.getStartDate() != null && myInfo.getEndDate() != null)
                myInfo = userRepository.save(myInfo);

            calendarAdapter.notifyDataSetChanged(); // 선택된 날짜를 기반으로 UI 갱신
        }, year, month, day).show();
    }

    private class CalendarAdapter extends BaseAdapter {
        private final List<String> dates;
        private final Calendar today;

        public CalendarAdapter(List<String> dates) {
            this.dates = dates;
            this.today = Calendar.getInstance();
        }

        @Override
        public int getCount() {
            return dates.size();
        }

        @Override
        public Object getItem(int position) {
            return dates.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                textView = new TextView(HomeActivity.this);
                textView.setLayoutParams(new GridView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                textView.setPadding(8, 8, 8, 8);
                textView.setTextSize(16);
            } else {
                textView = (TextView) convertView;
            }

            String date = dates.get(position);
            textView.setText(date);

            if (!date.isEmpty()) {
                int day = Integer.parseInt(date);

                // 오늘 날짜 색상
                if (day == today.get(Calendar.DAY_OF_MONTH)
                        && calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                        && calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
                    textView.setBackgroundColor(Color.GRAY);
                    textView.setTextColor(Color.WHITE);
                } else {
                    textView.setBackgroundColor(Color.TRANSPARENT);
                    textView.setTextColor(Color.DKGRAY);
                }

                // 여행 일정 날짜를 파란색으로 표시
                if (isTravelDate(day)) {
                    textView.setBackgroundColor(Color.BLUE);
                    textView.setTextColor(Color.WHITE);
                }
            } else {
                textView.setBackgroundColor(Color.TRANSPARENT);
                textView.setText("");
            }

            return textView;
        }

        private boolean isTravelDate(int day) {
            try {
                if (myInfo.getStartDate() == null && myInfo.getEndDate() == null) return false;
                String parsedDateString = currentMonthYear + "-" + day;
                Calendar date = Calendar.getInstance();
                date.setTime(DateUtils.parseDbDate(parsedDateString));
                Date start = myInfo.getStartDate();
                Date end = myInfo.getEndDate();
                return !date.getTime().before(start) && !date.getTime().after(end);
            } catch (Exception e) {
                return false;
            }
        }

    }
}