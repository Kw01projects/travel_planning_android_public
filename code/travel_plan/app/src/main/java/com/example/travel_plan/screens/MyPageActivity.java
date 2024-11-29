package com.example.travel_plan.screens;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_plan.R;
import com.example.travel_plan.entities.Place;
import com.example.travel_plan.entities.User;
import com.example.travel_plan.repositories.PlaceRepository;
import com.example.travel_plan.repositories.RepositoryFactory;
import com.example.travel_plan.repositories.UserRepository;

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

    // 레이아웃 관리
    private View travelLayout, ledgerLayout;

    // 데이터 저장
    private List<String> travelList, incomeList, expenseList;
    private Calendar calendar;
    private VisitedTravelAdapter travelAdapter;
    private UserRepository userRepository;
    private User myInfo;
    private PlaceRepository placeRepository;

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

        // 데이터 초기화
        travelList = new ArrayList<>();
        placeRepository = RepositoryFactory.getPlaceRepository(this);
        loadTravelList();
        incomeList = new ArrayList<>();
        expenseList = new ArrayList<>();
        calendar = Calendar.getInstance();

        // 어댑터 초기화
        travelAdapter = new VisitedTravelAdapter(travelList);
        travelRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        travelRecyclerView.setAdapter(travelAdapter);

        // 초기 화면 상태 설정
        travelLayout.setVisibility(View.GONE);
        ledgerLayout.setVisibility(View.GONE);

        // 여행지 버튼 클릭 이벤트
        travelButton.setOnClickListener(v -> {
            travelLayout.setVisibility(View.VISIBLE);
            ledgerLayout.setVisibility(View.GONE);
        });

        // 가계부 버튼 클릭 이벤트
        ledgerButton.setOnClickListener(v -> {
            travelLayout.setVisibility(View.GONE);
            ledgerLayout.setVisibility(View.VISIBLE);
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
                    Toast.makeText(this, "추가 실페되었습니다", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "여행지를 입력하세요", Toast.LENGTH_SHORT).show();
            }
        });

        // 이전 달 버튼 클릭 이벤트
        prevDateButton.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1);
            updateDate();
            resetLedger();
        });

        // 다음 달 버튼 클릭 이벤트
        nextDateButton.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, 1);
            updateDate();
            resetLedger();
        });

        // 수입 추가 버튼 클릭 이벤트
        addIncomeButton.setOnClickListener(v -> {
            String item = incomeItemInput.getText().toString().trim();
            String amount = incomeAmountInput.getText().toString().trim();
            if (TextUtils.isEmpty(item) || TextUtils.isEmpty(amount)) {
                Toast.makeText(this, "항목과 금액을 입력하세요.", Toast.LENGTH_SHORT).show();
            } else {
                incomeList.add(item + ": " + amount + "원");
                updateIncomeText();
                incomeItemInput.setText("");
                incomeAmountInput.setText("");
            }
        });

        // 지출 추가 버튼 클릭 이벤트
        addExpenseButton.setOnClickListener(v -> {
            String item = expenseItemInput.getText().toString().trim();
            String amount = expenseAmountInput.getText().toString().trim();
            if (TextUtils.isEmpty(item) || TextUtils.isEmpty(amount)) {
                Toast.makeText(this, "항목과 금액을 입력하세요.", Toast.LENGTH_SHORT).show();
            } else {
                expenseList.add(item + ": " + amount + "원");
                updateExpenseText();
                expenseItemInput.setText("");
                expenseAmountInput.setText("");
            }
        });

        // 현재 날짜 초기화
        updateDate();

        userRepository = RepositoryFactory.getUserRepository(this);
        myInfo = userRepository.getMyInfo();
        ((TextView) findViewById(R.id.nickname_text)).setText(myInfo.getNickName());
    }

    private void loadTravelList(){
        for(Place place : placeRepository.list())
            travelList.add(place.getPlace());
    }

    // 현재 날짜 업데이트
    private void updateDate() {
        int month = calendar.get(Calendar.MONTH) + 1;
        currentDateText.setText(month + "월");
    }

    // 수입/지출 초기화
    private void resetLedger() {
        incomeList.clear();
        expenseList.clear();
        updateIncomeText();
        updateExpenseText();
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
