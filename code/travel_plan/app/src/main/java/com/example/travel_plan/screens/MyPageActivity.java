package com.example.travel_plan.screens;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_plan.R;
import com.example.travel_plan.entities.MoneyLedger;
import com.example.travel_plan.entities.Place;
import com.example.travel_plan.entities.User;
import com.example.travel_plan.repositories.MoneyLedgerRepository;
import com.example.travel_plan.repositories.PlaceRepository;
import com.example.travel_plan.repositories.RepositoryFactory;
import com.example.travel_plan.repositories.UserRepository;
import com.example.travel_plan.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyPageActivity extends AppCompatActivity {

    // 여행지 관련 UI 요소
    private Button travelButton, ledgerButton, addTravelButton;
    private RecyclerView travelRecyclerView;
    private EditText travelInput;

    // 가계부 관련 UI 요소
    private Button prevDateButton, nextDateButton, addIncomeButton, addExpenseButton;
    private EditText incomeItemInput, incomeAmountInput, expenseItemInput, expenseAmountInput;
    private TextView incomeText, expenseText, currentDateText;

    // 날짜 네비게이션 관련 UI
    private HorizontalScrollView daysScrollContainer;
    private LinearLayout daysContainer;
    private LinearLayout dateNavigationLayout; // 날짜 네비게이션 전체 레이아웃

    // 레이아웃 관리
    private View travelLayout, ledgerLayout;

    // 데이터 저장
    private List<String> travelList, incomeList, expenseList;
    private Calendar calendar;
    private VisitedTravelAdapter travelAdapter;
    private UserRepository userRepository;
    private User myInfo;
    private PlaceRepository placeRepository;
    private MoneyLedgerRepository moneyLedgerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        // 여행지 UI 초기화
        travelButton = findViewById(R.id.travel_button);
        addTravelButton = findViewById(R.id.add_travel_button);
        travelRecyclerView = findViewById(R.id.travel_recyclerview);
        travelInput = findViewById(R.id.travel_input);
        travelLayout = findViewById(R.id.travel_layout);

        // 가계부 UI 초기화
        ledgerButton = findViewById(R.id.ledger_button);
        prevDateButton = findViewById(R.id.prev_date_button);
        nextDateButton = findViewById(R.id.next_date_button);
        addIncomeButton = findViewById(R.id.add_income_button);
        addExpenseButton = findViewById(R.id.add_expense_button);
        incomeItemInput = findViewById(R.id.income_item_input);
        incomeAmountInput = findViewById(R.id.income_amount_input);
        expenseItemInput = findViewById(R.id.expense_item_input);
        expenseAmountInput = findViewById(R.id.expense_amount_input);
        incomeText = findViewById(R.id.income_text);
        expenseText = findViewById(R.id.expense_text);
        currentDateText = findViewById(R.id.current_date_text);
        ledgerLayout = findViewById(R.id.ledger_layout);
        daysContainer = findViewById(R.id.days_container);
        daysScrollContainer = findViewById(R.id.date_scroll_view);
        dateNavigationLayout = findViewById(R.id.date_navigation_layout); // 날짜 네비게이션 레이아웃

        // 데이터 초기화
        travelList = new ArrayList<>();
        placeRepository = RepositoryFactory.getPlaceRepository(this);
        loadTravelList();

        moneyLedgerRepository = RepositoryFactory.getMoneyLedgerRepository(this);
        incomeList = new ArrayList<>();
        expenseList = new ArrayList<>();
        calendar = Calendar.getInstance();
        loadMoneyLedger();

        // 어댑터 초기화
        travelAdapter = new VisitedTravelAdapter(travelList);
        travelRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        travelRecyclerView.setAdapter(travelAdapter);

        // 초기 화면 상태 설정
        travelLayout.setVisibility(View.GONE);
        ledgerLayout.setVisibility(View.GONE);
        dateNavigationLayout.setVisibility(View.GONE); // 날짜 네비게이션은 초기에는 숨김

        // 여행지 버튼 클릭 이벤트
        travelButton.setOnClickListener(v -> {
            travelLayout.setVisibility(View.VISIBLE);
            ledgerLayout.setVisibility(View.GONE);
            dateNavigationLayout.setVisibility(View.GONE); // 날짜 네비게이션 숨김
        });

        // 가계부 버튼 클릭 이벤트
        ledgerButton.setOnClickListener(v -> {
            travelLayout.setVisibility(View.GONE);
            ledgerLayout.setVisibility(View.VISIBLE);
            dateNavigationLayout.setVisibility(View.VISIBLE); // 날짜 네비게이션 표시
            updateDateNavigation(); // 날짜 네비게이션 갱신
        });

        // 월 변경 버튼 클릭 이벤트
        prevDateButton.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1);
            updateDateNavigation();
        });

        nextDateButton.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, 1);
            updateDateNavigation();
        });

        // 여행지 추가 버튼 클릭 이벤트
        addTravelButton.setOnClickListener(v -> {
            String newTravel = travelInput.getText().toString().trim();
            if (!newTravel.isEmpty()) {
                try {
                    placeRepository.save(new Place(newTravel));
                    travelList.add(newTravel);
                    travelAdapter.notifyDataSetChanged();
                    travelInput.setText(""); // 입력 필드 초기화
                } catch (Exception ex) {
                    Toast.makeText(this, "추가 실패했습니다", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "여행지를 입력하세요", Toast.LENGTH_SHORT).show();
            }
        });

        // 수입 추가 버튼 클릭 이벤트
        addIncomeButton.setOnClickListener(v -> {
            String title = incomeItemInput.getText().toString().trim();
            String amount = incomeAmountInput.getText().toString().trim();
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(amount)) {
                Toast.makeText(this, "항목과 금액을 입력하세요.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    moneyLedgerRepository.save(new MoneyLedger(
                            title, Long.parseLong(amount), DateUtils.formatDbDate(calendar.getTime()), "IN"
                    ));
                    incomeList.add(title + ": " + amount + "원");
                    updateIncomeText();
                    incomeItemInput.setText("");
                    incomeAmountInput.setText("");
                } catch (Exception exception) {
                    Toast.makeText(this, "수입 추가가 실폐되었습니다.", Toast.LENGTH_SHORT).show();
                    System.err.println("수입 추가 실폐");
                    exception.printStackTrace();
                }
            }
        });

        // 지출 추가 버튼 클릭 이벤트
        addExpenseButton.setOnClickListener(v -> {
            String title = expenseItemInput.getText().toString().trim();
            String amount = expenseAmountInput.getText().toString().trim();
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(amount)) {
                Toast.makeText(this, "항목과 금액을 입력하세요.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    moneyLedgerRepository.save(new MoneyLedger(
                            title, Long.parseLong(amount), DateUtils.formatDbDate(calendar.getTime()), "OUT"
                    ));
                    expenseList.add(title + ": " + amount + "원");
                    updateExpenseText();
                    expenseItemInput.setText("");
                    expenseAmountInput.setText("");
                } catch (Exception exception) {
                    Toast.makeText(this, "지출 추가가 실폐되었습니다.", Toast.LENGTH_SHORT).show();
                    System.err.println("지출 추가 실폐");
                    exception.printStackTrace();
                }
            }
        });

        // 현재 날짜 초기화
        updateDateNavigation();

        // 사용자 정보 로드 및 닉네임 설정
        userRepository = RepositoryFactory.getUserRepository(this);
        myInfo = userRepository.getMyInfo();
        ((TextView) findViewById(R.id.nickname_text)).setText(myInfo.getNickName());
    }

    private void loadTravelList() {
        for (Place place : placeRepository.list())
            travelList.add(place.getPlace());
    }

    private void loadMoneyLedger() {
        for (MoneyLedger moneyLedger : moneyLedgerRepository.findByDateAndType(DateUtils.formatDbDate(calendar.getTime()), "IN"))
            incomeList.add(moneyLedger.getTitle() + ": " + moneyLedger.getAmount() + "원");
        for (MoneyLedger moneyLedger : moneyLedgerRepository.findByDateAndType(DateUtils.formatDbDate(calendar.getTime()), "OUT"))
            expenseList.add(moneyLedger.getTitle() + ": " + moneyLedger.getAmount() + "원");

        updateIncomeText();
        updateExpenseText();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density; // 화면 밀도 가져오기
        return Math.round(dp * density); // DP를 픽셀로 변환
    }

    // 날짜 네비게이션 갱신
    private void updateDateNavigation() {
        daysContainer.removeAllViews(); // 기존 날짜 버튼 제거

        Calendar tempCalendar = (Calendar) calendar.clone();
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1); // 월의 첫 번째 날짜로 설정
        int maxDay = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int scrollTo = 0;
        for (int i = 1; i <= maxDay; i++) {
            Button dayButton = new Button(this);
            final int selectedDay = i; // 람다에서 사용하기 위해 final로 설정
            dayButton.setText(String.valueOf(selectedDay));
            dayButton.setPadding(8, 4, 8, 4);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(40), // 버튼 너비 (예: 40dp)
                    dpToPx(40)  // 버튼 높이 (예: 40dp)
            );
            params.setMargins(0, 0, 0, 0);

            // 현재 날짜 강조
            if (selectedDay == calendar.get(Calendar.DAY_OF_MONTH)) {
                dayButton.setBackgroundResource(R.drawable.selected_date_background);
                scrollTo = dayButton.getLeft();
            } else {
                dayButton.setBackgroundResource(R.drawable.default_date_background);
            }

            dayButton.setOnClickListener(v -> {
                calendar.set(Calendar.DAY_OF_MONTH, selectedDay); // 선택된 날짜 설정
                updateDateNavigation(); // 날짜 강조 갱신
                resetLedger(); // 수입/지출 초기화
            });

            daysContainer.addView(dayButton);
        }

        updateDate(); // 월 정보 업데이트
//        daysScrollContainer.scrollTo(finalScrollTo, 0);
    }

    private void onDateSelected(int selectedDay) {
        calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
        resetLedger(); // 수입/지출 초기화
    }

    // 현재 월 업데이트
    private void updateDate() {
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        currentDateText.setText(month + "월 " + date + "일");
    }

    // 수입/지출 초기화
    private void resetLedger() {
        incomeList.clear();
        expenseList.clear();
        updateIncomeText();
        updateExpenseText();
        loadMoneyLedger();
    }

    // 수입 목록 업데이트
    private void updateIncomeText() {
        StringBuilder builder = new StringBuilder();
        for (String income : incomeList) {
            builder.append(income).append("\n");
        }
        incomeText.setText(builder.toString().trim());
    }

    // 지출 목록 업데이트
    private void updateExpenseText() {
        StringBuilder builder = new StringBuilder();
        for (String expense : expenseList) {
            builder.append(expense).append("\n");
        }
        expenseText.setText(builder.toString().trim());
    }
}