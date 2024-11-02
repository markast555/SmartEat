package com.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;

import android.widget.EditText;

import classes.DatabaseParams;
import classes.User;

public class EntranceActivity extends AppCompatActivity {

    private EditText enterEditTextLogin;
    private EditText enterEditTextPassword;//Для вывода

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        User user = new User();
        DatabaseParams.setUser_se(user);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.entrance_frame);

        // Получаем ссылку на EditText
        enterEditTextLogin = findViewById(R.id.entranceEditTextLogin);
        enterEditTextPassword = findViewById(R.id.entranceEditTextPassword);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        enterEditTextLogin = findViewById(R.id.entranceEditTextLogin);
        enterEditTextPassword = findViewById(R.id.entranceEditTextPassword);

        addTextWatcher(enterEditTextLogin);
        addTextWatcher(enterEditTextPassword);

    }

    // Метод для добавления TextWatcher
    private void addTextWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adjustTextSize(editText);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Метод для изменения размера текста
    private void adjustTextSize(EditText editText) {
        int height = editText.getHeight();
        if (height > 0) {
            // Пример вычисления размера шрифта
            float textSize = height / 10f; // Измените делитель по необходимости
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }
    }

    public void toRegistration(View v){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    // Пример метода для получения текста логина
    public String getLogin() {
        return enterEditTextLogin.getText().toString();
    }

    public String getPassword() {
        return enterEditTextPassword.getText().toString();
    }

    // Обработка клика на кнопку "Войти"
    public void onButtonEnterClick(View v) {

        String login = getLogin();
        String password = getPassword();

        System.out.println("Логин: " + login);
        System.out.println("Пароль: " + password);
    }

}

