package com.example.activities;

import static android.os.SystemClock.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextPassword;
    private EditText editTextPassword2;
    private EditText editTextPhoneNumber;
    private CheckBox checkBoxConditions;
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

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        checkBoxConditions = findViewById(R.id.checkBoxConditions);
        buttonContinue = findViewById(R.id.buttonContinue);

        setupPhoneNumberFormatting();

        buttonContinue.setOnClickListener(this::onContinueButtonClick);

        addTextWatcher(editTextPassword);
        addTextWatcher(editTextLogin);
        addTextWatcher(editTextPassword2);
        addTextWatcher(editTextPhoneNumber);
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


    private void setupPhoneNumberFormatting() {
        editTextPhoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !isPhoneNumberInitialized) {
                editTextPhoneNumber.setText("+7-");
                editTextPhoneNumber.setSelection(editTextPhoneNumber.getText().length()); // Устанавливаем курсор в конец
                isPhoneNumberInitialized = true; // Устанавливаем флаг, чтобы не добавлять снова
            }
        });

        editTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            private boolean mFormatting; // флаг для предотвращения бесконечных вызовов
            private int mLastStartLocation;
            private String mLastBeforeText;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mLastStartLocation = start;
                mLastBeforeText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Ничего не делаем
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mFormatting) {
                    return;
                }

                mFormatting = true;
                try {
                    String currentValue = s.toString();


                    // Запрет на удаление "+7-"
                    if (currentValue.length() < 4) {
                        editTextPhoneNumber.setText("+7-");
                        editTextPhoneNumber.setSelection(3); // Устанавливаем курсор после "+7-"
                        return;
                    }

                    // Если номер уже начинается с "+7-", форматируем оставшуюся часть
                    if (!currentValue.startsWith("+7-")) {
                        currentValue = "+7-" + currentValue; // Добавляем префикс, если его нет
                    }

                    // Форматируем номер
                    String formattedValue = formatRuNumber(currentValue);

                    // Ограничиваем ввод до 16 символов
                    if (formattedValue.length() > 16) {
                        formattedValue = formattedValue.substring(0, 16);
                    }

                    editTextPhoneNumber.setText(formattedValue);
                    editTextPhoneNumber.setSelection(formattedValue.length()); // Устанавливаем курсор в конец

                } catch (Exception e) {
                    e.printStackTrace(); // Логируем ошибку
                } finally {
                    mFormatting = false;
                }
            }

            private String formatRuNumber(String text) {
                // Удаляем все символы, кроме цифр
                String digits = text.replaceAll("[^\\d]", "").substring(1);

                // Ограничиваем длину до 10 цифр
                if (digits.length() > 10) {
                    digits = digits.substring(0, 10);
                }

                StringBuilder formattedString = new StringBuilder("+7-");

                // Форматируем номер
                if (digits.length() > 0) {
                    formattedString.append(digits);
                }

                if (digits.length() > 3) {
                    formattedString.insert(6, "-");
                }
                if (digits.length() > 6) {
                    formattedString.insert(10, "-");
                }
                if (digits.length() > 8) {
                    formattedString.insert(13, "-");
                }

                return formattedString.toString();
            }
        });
    }


    private void onContinueButtonClick(View v) {
        String input = editTextLogin.getText().toString().trim();
        if (isValidLogin(input)) {
            editTextLogin.setError(null); // Убираем ошибку
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
                        if (checkBoxConditions.isChecked()){
                            checkBoxConditions.setTextColor(getResources().getColor(R.color.dark_text,
                                    getTheme()));
                            Toast.makeText(this, "Принято!", Toast.LENGTH_SHORT).show();
                        }else {
                            checkBoxConditions.setTextColor(getResources().getColor(R.color.red,
                                    getTheme()));
                            Toast.makeText(this,
                                    "Пожалуйста, примите условия предоставления услуг",
                                    Toast.LENGTH_LONG).show();
                            sleep(1000);
                        }
                    }else {
                        editTextPhoneNumber.setError("Номер телефона: +7-XXX-XXX-XX-XX");
                        Toast.makeText(this, "Номер телефона: +7-XXX-XXX-XX-XX",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    editTextPassword2.setError("Пароли не совпадают");
                    Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG)
                            .show();
                }

            }else {
                editTextPassword.setError("Пароль: длина (4-16), смволы (a-zA-Z0-9_)");
                Toast.makeText(this, "Пароль: длина (4-16), смволы (a-zA-Z0-9_)",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            editTextLogin.setError("Логин: длина (4-16), смволы (a-zA-Z0-9_)");
            Toast.makeText(this, "Логин: длина (4-16), смволы (a-zA-Z0-9_)",
                    Toast.LENGTH_LONG).show();
        }
    }


    private boolean isValidLogin(String login) {
        return login.length() >= 4 && login.length() <= 16 && login.matches("[a-zA-Z0-9_]+");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}");
    }
}
