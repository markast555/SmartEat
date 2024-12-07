package com.example.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
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

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Calendar;

import classes.DatabaseParams;
import classes.Goals;
import classes.PhysicalActivityLevel;
import classes.Sex;
import classes.User;
import classes.UserRepositoryCrud;
import classes.VariableGenerator;

public class ProfileAfterRegistrationActivity extends AppCompatActivity {


    private TextView textViewFillOutProfile;
    private TextView textViewSexAfter;
    private EditText editTextDateOfBirthAfter;
    private TextView textViewHeight;
    private EditText editTextHeight;
    private TextView textViewWeight;
    private EditText editTextWeight;
    private AutoCompleteTextView autoCompleteTextViewLevelOfPhysicalActivityAfter;
    private AutoCompleteTextView autoCompleteTextViewGoalAfter;
    private ImageButton imageButtonQuestionAfter;
    private User user = null;
    private Sex sex = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        user = getIntent().getParcelableExtra("user"); // Получаем объект User

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_after_registration_frame);

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

        textViewFillOutProfile = findViewById(R.id.textViewFillOutProfile);
        textViewSexAfter = findViewById(R.id.textViewSexAfter);
        editTextDateOfBirthAfter = findViewById(R.id.editTextDateOfBirthAfter);
        textViewHeight = findViewById(R.id.textViewHeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        textViewWeight = findViewById(R.id.textViewWeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        autoCompleteTextViewLevelOfPhysicalActivityAfter = findViewById(R.id.
                autoCompleteTextViewLevelOfPhysicalActivityAfter);
        autoCompleteTextViewGoalAfter = findViewById(R.id.autoCompleteTextViewGoalAfter);
        imageButtonQuestionAfter = findViewById(R.id.imageButtonQuestionAfter);

//        ImageButton imageButtonHome = findViewById(R.id.imageButtonHome);
//        imageButtonHome.setOnClickListener(v -> {
//            Intent intent = new Intent(ProfileAfterRegistrationActivity.this, MainActivity.class);
//            startActivity(intent);
//            overridePendingTransition(0, 0); // Отключает анимацию перехода
//            finish(); // Завершает ProfileActivity
//        });

        addTextWatcherTextView(textViewFillOutProfile);
        addTextWatcherTextView(textViewSexAfter);
        addTextWatcherEditText(editTextDateOfBirthAfter);
        addTextWatcherTextView(textViewHeight);
        addTextWatcherEditText(editTextHeight);
        addTextWatcherTextView(textViewWeight);
        addTextWatcherEditText(editTextWeight);

        editTextDateOfBirthAfter.setOnClickListener(v -> showDatePicker());

        setupPhysicalActivityLevelDropdown();
        setupGoalDropdown();

        imageButtonQuestionAfter.setOnClickListener(v -> showPhysicalActivityInfoDialog());

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

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // Установить выбранную дату в EditText
                    editTextDateOfBirthAfter.setText(selectedDay + "." + (selectedMonth + 1) + "." + selectedYear);
                }, year, month, day);
        datePickerDialog.show();
    }


    private void setupPhysicalActivityLevelDropdown() {
        // Получаем строки из перечисления
        String[] physicalActivityLevels = new String[PhysicalActivityLevel.values().length];
        for (int i = 0; i < PhysicalActivityLevel.values().length; i++) {
            physicalActivityLevels[i] = PhysicalActivityLevel.values()[i].getType();
        }

        // Настройка адаптера для AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, physicalActivityLevels);
        autoCompleteTextViewLevelOfPhysicalActivityAfter.setAdapter(adapter);

        // Запретить ввод текста
        autoCompleteTextViewLevelOfPhysicalActivityAfter.setFocusable(false);
        autoCompleteTextViewLevelOfPhysicalActivityAfter.setOnClickListener(v -> autoCompleteTextViewLevelOfPhysicalActivityAfter.showDropDown());
    }


    private void setupGoalDropdown() {
        // Получаем строки из перечисления
        String[] goals = new String[Goals.values().length];
        for (int i = 0; i < Goals.values().length; i++) {
            goals[i] = Goals.values()[i].getType();
        }

        // Настройка адаптера для AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, goals);
        autoCompleteTextViewGoalAfter.setAdapter(adapter);

        // Запретить ввод текста
        autoCompleteTextViewGoalAfter.setFocusable(false);
        autoCompleteTextViewGoalAfter.setOnClickListener(v -> autoCompleteTextViewGoalAfter.showDropDown());
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

    public void toMain(View v){
        System.out.println(user.toString());

        System.out.println("sex = " + sex);
        System.out.println("editTextDateOfBirthAfter = " + editTextDateOfBirthAfter.getText());
        System.out.println("editTextHeight = " + editTextHeight.getText());
        System.out.println("editTextWeight = " + editTextWeight.getText());
        System.out.println("autoCompleteTextViewLevelOfPhysicalActivityAfter = " + autoCompleteTextViewLevelOfPhysicalActivityAfter.getText());
        System.out.println("autoCompleteTextViewGoalAfter = " + autoCompleteTextViewGoalAfter.getText());

        if (sex != null && !editTextDateOfBirthAfter.getText().toString().equals("") &&  !editTextHeight.getText().toString().equals("") && !editTextWeight.getText().toString().equals("") && !autoCompleteTextViewLevelOfPhysicalActivityAfter.getText().toString().equals("") && !autoCompleteTextViewGoalAfter.getText().toString().equals("")) {
            try {

                String str = String.valueOf(editTextDateOfBirthAfter.getText());
                String[] parts = str.split("\\.");
                int dayOfMonth = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);
                user.setDateOfBirth(LocalDate.of(year, month, dayOfMonth));


                user.setIdUser(VariableGenerator.getUid());
                user.setSex(sex);
                user.setHeight(Integer.parseInt(editTextHeight.getText().toString()));
                user.setWeight(Float.parseFloat(editTextWeight.getText().toString()));
                user.setLevelOfPhysicalActivity(PhysicalActivityLevel.fromType(autoCompleteTextViewLevelOfPhysicalActivityAfter.getText().toString()));
                user.setGoals(Goals.fromType(autoCompleteTextViewGoalAfter.getText().toString()));

                System.out.println("Готово!!!!");
                System.out.println(user.toString());
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            new Thread(new Runnable() {
                @Override
                public void run() {

                    UserRepositoryCrud userRepositoryCrud = new UserRepositoryCrud();
                    userRepositoryCrud.setConnectionParameters(DatabaseParams.getUrl(), DatabaseParams.getUser(), DatabaseParams.getPassword());
                    boolean isCreate = false;
                    try {
                        System.out.println(user.toString());
                        isCreate = userRepositoryCrud.create(user);
                    } catch (SQLException e) {
                        System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
                        showInfo("Ошибка соединения");
                    } catch (ClassNotFoundException e) {
                        System.out.println("Драйвер базы данных не найден: " + e.getMessage());
                        showInfo("Ошибка соединения");
                    }

                    if (isCreate) {
                        showInfo("Аккаунт создан");
                        Intent intent = new Intent(ProfileAfterRegistrationActivity.this, MainActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                }
            }).start();

        } else{
            Toast.makeText(ProfileAfterRegistrationActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
        }
    }

    private void showInfo(String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ProfileAfterRegistrationActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void clickOnMale(View view) {
        this.sex = Sex.MALE;
    }

    public void clickOnFemale(View view) {
        this.sex = Sex.FEMALE;
    }
}