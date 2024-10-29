package com.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;

import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText enterEditTextLogin;
    private EditText enterEditTextPassword;

    private static final String TAG = "MainActivity"; //Для вывода

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Я в onCreate(Main)");
        //Log.d(TAG, "Я в onCreate(Main)");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.entrance_frame);

        // Получаем ссылку на EditText
        enterEditTextLogin = findViewById(R.id.enterEditTextLogin);
        enterEditTextPassword = findViewById(R.id.enterEditTextPassword);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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

        Log.d(TAG, "Логин: " + login);
        Log.d(TAG, "Пароль: " + password);
    }


}