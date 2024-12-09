package com.example.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.SQLException;
import java.util.Objects;

import classes.DatabaseParams;
import classes.Goals;
import classes.PhysicalActivityLevel;
import classes.User;
import classes.UserRepositoryCrud;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewLogin;
    private EditText editTextLogin;

    private EditText editTextEmail;
    private TextView textViewHeight;
    private EditText editTextHeight;
    private TextView textViewWeight;
    private EditText editTextWeight;
    private AutoCompleteTextView autoCompleteTextViewLevelOfPhysicalActivity;
    private AutoCompleteTextView autoCompleteTextViewGoal;
    private ImageButton imageButtonQuestion;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        user = getIntent().getParcelableExtra("user");
        System.out.println(user.toString());

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_frame);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.main));

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

        textViewLogin = findViewById(R.id.textViewLogin);
        editTextLogin = findViewById(R.id.editTextLogin);

        editTextEmail = findViewById(R.id.editTextEmail);
        textViewHeight = findViewById(R.id.textViewHeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        textViewWeight = findViewById(R.id.textViewWeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        autoCompleteTextViewLevelOfPhysicalActivity = findViewById(R.id.
                autoCompleteTextViewLevelOfPhysicalActivity);
        autoCompleteTextViewGoal = findViewById(R.id.autoCompleteTextViewGoal);
        imageButtonQuestion = findViewById(R.id.imageButtonQuestion);

        ImageButton imageButtonHome = findViewById(R.id.imageButtonHome);
        imageButtonHome.setOnClickListener(v -> navigateToMainActivity());

        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(v -> navigateToMainActivity());

        ImageButton imageButtonDiary = findViewById(R.id.imageButtonDiary);
        imageButtonDiary.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, DiaryActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });

        editTextLogin.setText(user.getLogin());
        editTextEmail.setText(user.getGmail());
        editTextHeight.setText(String.valueOf(user.getHeight()));
        editTextWeight.setText(String.valueOf(user.getWeight()));
        autoCompleteTextViewLevelOfPhysicalActivity.setText(user.getLevelOfPhysicalActivity().getType(), false);
        autoCompleteTextViewGoal.setText(user.getGoals().getType(), false);

        addTextWatcherTextView(textViewLogin);
        addTextWatcherEditText(editTextLogin);

        addTextWatcherEditText(editTextEmail);
        addTextWatcherTextView(textViewHeight);
        addTextWatcherEditText(editTextHeight);
        addTextWatcherTextView(textViewWeight);
        addTextWatcherEditText(editTextWeight);

        setupPhysicalActivityLevelDropdown();
        setupGoalDropdown();

        imageButtonQuestion.setOnClickListener(v -> showPhysicalActivityInfoDialog());

    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        overridePendingTransition(0, 0); // Отключает анимацию перехода
        finish(); // Завершает ProfileActivity
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

    // Метод для добавления TextWatcher к TextView
    private void addTextWatcherTextView(TextView textView) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adjustTextSizeTextView(textView);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Метод для изменения размера текста в TextView
    private void adjustTextSizeTextView(TextView textView) {
        int height = textView.getHeight();
        if (height > 0) {
            // Пример вычисления размера шрифта
            float textSize = height / 10f; // Измените делитель по необходимости
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }
    }


    private void setupPhysicalActivityLevelDropdown() {
        // Получаем строки из перечисления
        String[] physicalActivityLevels = new String[PhysicalActivityLevel.values().length];
        for (int i = 0; i < PhysicalActivityLevel.values().length; i++) {
            physicalActivityLevels[i] = PhysicalActivityLevel.values()[i].getType();
        }

        // Настройка адаптера для AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, physicalActivityLevels);
        autoCompleteTextViewLevelOfPhysicalActivity.setAdapter(adapter);

        // Запретить ввод текста
        autoCompleteTextViewLevelOfPhysicalActivity.setFocusable(false);
        autoCompleteTextViewLevelOfPhysicalActivity.setOnClickListener(v -> autoCompleteTextViewLevelOfPhysicalActivity.showDropDown());
    }


    private void setupGoalDropdown() {
        // Получаем строки из перечисления
        String[] goals = new String[Goals.values().length];
        for (int i = 0; i < Goals.values().length; i++) {
            goals[i] = Goals.values()[i].getType();
        }

        // Настройка адаптера для AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, goals);
        autoCompleteTextViewGoal.setAdapter(adapter);

        // Запретить ввод текста
        autoCompleteTextViewGoal.setFocusable(false);
        autoCompleteTextViewGoal.setOnClickListener(v -> autoCompleteTextViewGoal.showDropDown());
    }

//    private void showPhysicalActivityInfoDialog() {
//        BottomSheetDialog dialog = new BottomSheetDialog(this);
//        View dialogView = getLayoutInflater().inflate(R.layout.physical_activity_info_sheet, null);
//        dialog.setContentView(dialogView);
//
//        ImageButton buttonClose = dialogView.findViewById(R.id.imageButtonClose);
//        buttonClose.setOnClickListener(v -> dialog.dismiss());
//
//        dialog.show();
//    }

    private void showPhysicalActivityInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.physical_activity_info_sheet, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);

        ImageButton buttonClose = dialogView.findViewById(R.id.imageButtonClose);
        buttonClose.setOnClickListener(v -> dialog.dismiss());

        // Центрирование диалогового окна
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.show();
    }

    public void toMainFromProfile(View v) throws CloneNotSupportedException {

        System.out.println(user.toString());
        System.out.println("editTextLogin = " + editTextLogin.getText());
        System.out.println("editTextEmail = " + editTextEmail.getText());
        System.out.println("editTextHeight = " + editTextHeight.getText());
        System.out.println("editTextWeight = " + editTextWeight.getText());
        System.out.println("autoCompleteTextViewLevelOfPhysicalActivity = " + autoCompleteTextViewLevelOfPhysicalActivity.getText());
        System.out.println("autoCompleteTextViewGoal = " + autoCompleteTextViewGoal.getText());

        User newUser = (User) user.clone();
        newUser.setLogin(editTextLogin.getText().toString());
        newUser.setGmail(editTextEmail.getText().toString());
        newUser.setHeight(Integer.parseInt(editTextHeight.getText().toString()));
        newUser.setWeight(Float.parseFloat(editTextWeight.getText().toString()));
        newUser.setLevelOfPhysicalActivity(PhysicalActivityLevel.fromType(autoCompleteTextViewLevelOfPhysicalActivity.getText().toString()));
        newUser.setGoals(Goals.fromType(autoCompleteTextViewGoal.getText().toString()));

        if (newUser.hashCode() != user.hashCode()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserRepositoryCrud userRepositoryCrud = new UserRepositoryCrud();
                    userRepositoryCrud.setConnectionParameters(DatabaseParams.getUrl(), DatabaseParams.getUser(), DatabaseParams.getPassword());

                    boolean loginExist = false;
                    boolean emailExist = false;
                    if (!Objects.equals(newUser.getLogin(), user.getLogin())){
                        try {
                            loginExist = userRepositoryCrud.checkByOneField(newUser.getLogin(), "login");
                        } catch (SQLException e) {
                            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
                            showInfo("Ошибка соединения");
                        } catch (ClassNotFoundException e) {
                            System.out.println("Драйвер базы данных не найден: " + e.getMessage());
                            showInfo("Ошибка соединения");
                        }
                        if (loginExist){
                            System.out.println("Такой логин уже существует");
                            showInfo("Такой логин уже существует");
                        }
                    }
                    if (!Objects.equals(newUser.getGmail(), user.getGmail())){
                        try {
                            emailExist = userRepositoryCrud.checkByOneField(newUser.getGmail(), "gmail");
                        } catch (SQLException e) {
                            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
                            showInfo("Ошибка соединения");
                        } catch (ClassNotFoundException e) {
                            System.out.println("Драйвер базы данных не найден: " + e.getMessage());
                            showInfo("Ошибка соединения");
                        }
                        if (emailExist){
                            System.out.println("Такой email уже существует");
                            showInfo("Такой email уже существует");
                        }
                    }
                    if (!loginExist && !emailExist){
                        int result = 0;
                        try {
                            result = userRepositoryCrud.update(newUser);
                        } catch (SQLException e) {
                            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
                            showInfo("Ошибка соединения");
                        } catch (ClassNotFoundException e) {
                            System.out.println("Драйвер базы данных не найден: " + e.getMessage());
                            showInfo("Ошибка соединения");
                        }
                        if (result != 0){
                            try {
                                user = (User) newUser.clone();
                                System.out.println("Данные изменены");
                                showInfo("Данные изменены");
                            } catch (CloneNotSupportedException e) {
                                System.out.println("Данные не перенеслись в основной экземпляр класса User: " + e.getMessage());
                                showInfo("Ошибка соединения");
                            }
                        }else{
                            System.out.println("Ошибка подключения к базе данных");
                            showInfo("Ошибка соединения");
                        }
                    }
                }
            }).start();
        }else{
            System.out.println("Изменений не найдено");
        }
    }

    private void showInfo(String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ProfileActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

