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

import android.widget.EditText;
import android.widget.Toast;


import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import classes.DatabaseParams;
import classes.User;
import classes.UserRepositoryCrud;
import classes.VariableGenerator;

public class EntranceActivity extends AppCompatActivity {

    private EditText enterEditTextLogin;
    private EditText enterEditTextPassword;//Для вывода
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        System.out.println("Я в onCreate");
//        try {
//            User user = getIntent().getParcelableExtra("user"); // Получаем объект User
//        } catch (NullPointerException e) {
//            // Обработка NullPointerException
//            System.out.println("Caught NullPointerException: " + e.getMessage());
//        } catch (Exception e) {
//            // Обработка других исключений, если необходимо
//            System.out.println("Caught Exception: " + e.getMessage());
//        }

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

        setupUI();
    }

    private void setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        enterEditTextLogin = findViewById(R.id.entranceEditTextLogin);
        enterEditTextPassword = findViewById(R.id.entranceEditTextPassword);

        addTextWatcherEditText(enterEditTextLogin);
        addTextWatcherEditText(enterEditTextPassword);

    }

    // Метод для добавления TextWatcher
    private void addTextWatcherEditText(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adjustTextSizeEditText(editText);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Метод для изменения размера текста
    private void adjustTextSizeEditText(EditText editText) {
        int height = editText.getHeight();
        if (height > 0) {
            // Пример вычисления размера шрифта
            float textSize = height / 10f; // Измените делитель по необходимости
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }
    }

    public void toRegistration(View v){
        Intent intent = new Intent(EntranceActivity.this, RegistrationActivity.class);
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepositoryCrud userRepositoryCrud = new UserRepositoryCrud();
                userRepositoryCrud.setConnectionParameters(DatabaseParams.getUrl(), DatabaseParams.getUser(), DatabaseParams.getPassword());

                try {
                    user = userRepositoryCrud.selectByLogin(login);
                } catch (SQLException e) {
                    System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
                    showInfo("Ошибка соединения");
                } catch (ClassNotFoundException e) {
                    System.out.println("Драйвер базы данных не найден: " + e.getMessage());
                    showInfo("Ошибка соединения");
                }
                if (user == null){
                    System.out.println("Неверный логин");
                    showInfo("Неверный логин");

                }else{

                    if (VariableGenerator.checkPassword(password, user.getPassword())){
                        System.out.println("Неверный пароль");
                        showInfo("Неверный пароль");
                    }else{
                        System.out.println("Всё хорошо");
                        showInfo("Вход выполнен");
                        Intent intent = new Intent(EntranceActivity.this, MainActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                }
            }
        }).start();
    }

    private void showInfo(String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(EntranceActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean isValidLogin(String login) {
        return login.length() >= 4 && login.length() <= 16 && login.matches("[a-zA-Z0-9_]+");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 4 && password.length() <= 16 && password.matches("[a-zA-Z0-9_]+");
    }

}