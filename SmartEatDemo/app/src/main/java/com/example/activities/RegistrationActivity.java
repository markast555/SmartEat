package com.example.activities;

import static android.os.SystemClock.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import classes.User;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPassword2;
    private CheckBox checkBoxConditions;
    private Button buttonContinue;

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

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextEmail = findViewById(R.id.editTextEmail); // Новое поле для email
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        checkBoxConditions = findViewById(R.id.checkBoxConditions);
        buttonContinue = findViewById(R.id.buttonContinue);

        buttonContinue.setOnClickListener(this::onContinueButtonClick);

        addTextWatcherEditText(editTextPassword);
        addTextWatcherEditText(editTextLogin);
        addTextWatcherEditText(editTextPassword2);
        addTextWatcherEditText(editTextEmail);
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

    private void onContinueButtonClick(View v) {
        String input = editTextLogin.getText().toString().trim();
        if (isValidLogin(input)) {
            editTextLogin.setError(null); // Убираем ошибку

            input = editTextPassword.getText().toString().trim();
            if (isValidPassword(input)) {
                editTextPassword.setError(null);
                String input2 = editTextPassword2.getText().toString().trim();
                if (input.equals(input2)) {
                    editTextPassword2.setError(null);

                    String emailInput = editTextEmail.getText().toString().trim();
                    if (isValidEmail(emailInput)) {
                        editTextEmail.setError(null);

                        if (checkBoxConditions.isChecked()) {
                            checkBoxConditions.setTextColor(getResources().getColor(R.color.dark_text,
                                    getTheme()));
                            Toast.makeText(this, "Принято!", Toast.LENGTH_SHORT).show();

                            // Переход в EmailVerificationActivity с передачей email
                            try {
                                Intent intent = new Intent(this, EmailVerificationActivity.class);
                                User user = new User(null, editTextLogin.getText().toString(), editTextPassword.getText().toString(), null, null, 0, 0, null, null, editTextEmail.getText().toString(), 0);
                                intent.putExtra("user", user);
                                intent.putExtra("userEmail", emailInput); // Передача email
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(this, "Ошибка при переходе в EmailVerificationActivity", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            checkBoxConditions.setTextColor(getResources().getColor(R.color.red,
                                    getTheme()));
                            Toast.makeText(this,
                                    "Пожалуйста, примите условия предоставления услуг",
                                    Toast.LENGTH_LONG).show();
                            sleep(1000);
                        }
                    } else {
                        editTextEmail.setError("Введите корректный email");
                        Toast.makeText(this, "Введите корректный email", Toast.LENGTH_LONG).show();
                    }
                } else {
                    editTextPassword2.setError("Пароли не совпадают");
                    Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG)
                            .show();
                }
            } else {
                editTextPassword.setError("Пароль: длина (4-16), символы (a-zA-Z0-9_)");
                Toast.makeText(this, "Пароль: длина (4-16), символы (a-zA-Z0-9_)",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            editTextLogin.setError("Логин: длина (4-16), символы (a-zA-Z0-9_)");
            Toast.makeText(this, "Логин: длина (4-16), символы (a-zA-Z0-9_)",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValidLogin(String login) {
        return login.length() >= 4 && login.length() <= 16 && login.matches("[a-zA-Z0-9_]+");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 4 && password.length() <= 16 && password.matches("[a-zA-Z0-9_]+");
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}