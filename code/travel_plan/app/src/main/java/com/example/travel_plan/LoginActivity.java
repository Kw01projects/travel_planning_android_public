package com.example.travel_plan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travel_plan.entities.User;
import com.example.travel_plan.repositories.RepositoryFactory;
import com.example.travel_plan.repositories.UserRepository;
import com.example.travel_plan.screens.HomeActivity;
import com.example.travel_plan.viewModels.UserViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {

    private UserRepository userRepository; // UserRepository 객체 선언
    private User myInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("Start first");

        RepositoryFactory.init(this);
        userRepository = RepositoryFactory.getUserRepository(this);
        myInfo = userRepository.getMyInfo();

        if (myInfo != null && !myInfo.getNickName().isEmpty()) // require user input their nickname
            startHomeActivity();

        // 닉네임 입력 필드와 버튼 참조
        EditText nicknameInput = findViewById(R.id.nickname_input);
        Button startButton = findViewById(R.id.start_button);

        // 시작 버튼 클릭 이벤트
        startButton.setOnClickListener(v -> {
            String nickname = nicknameInput.getText().toString().trim();
            if (nickname.isEmpty())
                Toast.makeText(this, "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
            else {
                if (!myInfo.getNickName().equals(nickname)) {
                    myInfo.setNickName(nickname);
                    myInfo = userRepository.save(myInfo);
                }
                startHomeActivity();
            }
        });
    }

    private void startHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        (new ViewModelProvider(this)
                .get(UserViewModel.class)).setUser(myInfo);
    }
}
