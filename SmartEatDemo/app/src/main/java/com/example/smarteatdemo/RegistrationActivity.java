package com.example.smarteatdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutLogin;
    private EditText editTextLogin;
    private EditText editTextPassword;
    private EditText editTextPassword2;
    private EditText editTextPhoneNumber;
    private Button buttonContinue;

    private boolean isPhoneNumberInitialized = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registration_frame);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupUI();
    }

    public void toEntrance(View v){
        finish();
    }

    private void setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textInputLayoutLogin = findViewById(R.id.textInputLayoutLogin);
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonContinue = findViewById(R.id.buttonContinue);

        setupPhoneNumberFormatting();

        buttonContinue.setOnClickListener(this::onContinueButtonClick);
    }

    private void onContinueButtonClick(View v) {
        String input = editTextLogin.getText().toString().trim();
        if (isValidLogin(input)) {
            textInputLayoutLogin.setError(null); // Убираем ошибку
            //Toast.makeText(this, "Логин принят!", Toast.LENGTH_SHORT).show();
            // Логика для перехода на следующий экран
            // Например:
            // Intent intent = new Intent(this, NextActivity.class);
            // startActivity(intent);
            input = editTextPassword.getText().toString().trim();
            if (isValidLogin(input)) {
                editTextPassword.setError(null);
                String input2 = editTextPassword2.getText().toString().trim();
                if (input.equals(input2)){
                    editTextPassword2.setError(null);
                    input = editTextPhoneNumber.getText().toString().trim();
                    if (isValidPhoneNumber(input)) {
                        editTextPhoneNumber.setError(null);
                        Toast.makeText(this, "Принят!", Toast.LENGTH_SHORT).show();
                    }else {
                        editTextPhoneNumber.setError("Номер телефона: +7-***-***-**-**");
                        Toast.makeText(this, "Номер телефона: +7-***-***-**-**", Toast.LENGTH_LONG).show();
                    }
                }else{
                    editTextPassword2.setError("Пароли не совпадают");
                    Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG).show();
                }

            }else {
                editTextPassword.setError("Пароль: длина (4-16), смволы (a-zA-Z0-9_)");
                Toast.makeText(this, "Пароль: длина (4-16), смволы (a-zA-Z0-9_)", Toast.LENGTH_LONG).show();
            }

        } else {
            textInputLayoutLogin.setError("Логин: длина (4-16), смволы (a-zA-Z0-9_)");
            Toast.makeText(this, "Логин: длина (4-16), смволы (a-zA-Z0-9_)", Toast.LENGTH_LONG).show();
        }
    }

    private void setupPhoneNumberFormatting() {
        editTextPhoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !isPhoneNumberInitialized) {
                editTextPhoneNumber.setText("+7-");
                editTextPhoneNumber.setSelection(editTextPhoneNumber.getText().length()); // Устанавливаем курсор в конец
                isPhoneNumberInitialized = true; // Устанавливаем флаг, чтобы не добавлять снова
            }
        });

        editTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Ничего не делаем
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Ничего не делаем
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) {
                    return;
                }

                String input = s.toString().replaceAll("[^\\d]", ""); // Удаляем все символы, кроме цифр

                // Ограничиваем длину до 10 цифр
                if (input.length() > 10) {
                    input = input.substring(0, 10);
                }

                StringBuilder formatted = new StringBuilder("+7-");

                // Форматируем номер с учетом введенных цифр
                if (input.length() > 0) {
                    formatted.append(input);
                }

                if (input.length() > 3) {
                    formatted.insert(6, "-");
                }
                if (input.length() > 6) {
                    formatted.insert(10, "-");
                }
                if (input.length() > 8) {
                    formatted.insert(13, "-");
                }

                isFormatting = true;
                editTextPhoneNumber.setText(formatted.toString());
                editTextPhoneNumber.setSelection(formatted.length()); // Устанавливаем курсор в конец
                isFormatting = false;
            }
        });
    }

    private boolean isValidLogin(String login) {
        return login.length() >= 4 && login.length() <= 16 && login.matches("[a-zA-Z0-9_]+");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}");
    }
}