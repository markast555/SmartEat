package com.example.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;

import classes.DatabaseParams;
import classes.Goals;
import classes.PhysicalActivityLevel;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewLogin;
    private EditText editTextLogin;
    private TextView textViewSex;
    private EditText editTextDateOfBirth;
    private TextView textViewHeight;
    private EditText editTextHeight;
    private TextView textViewWeight;
    private EditText editTextWeight;
    private AutoCompleteTextView autoCompleteTextViewLevelOfPhysicalActivity;
    private AutoCompleteTextView autoCompleteTextViewGoal;
    private ImageButton imageButtonQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println(DatabaseParams.getUser_se());

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
        textViewSex = findViewById(R.id.textViewSex);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        textViewHeight = findViewById(R.id.textViewHeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        textViewWeight = findViewById(R.id.textViewWeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        autoCompleteTextViewLevelOfPhysicalActivity = findViewById(R.id.
                autoCompleteTextViewLevelOfPhysicalActivity);
        autoCompleteTextViewGoal = findViewById(R.id.autoCompleteTextViewGoal);
        imageButtonQuestion = findViewById(R.id.imageButtonQuestion);

        ImageButton imageButtonHome = findViewById(R.id.imageButtonHome);
        imageButtonHome.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });

        addTextWatcherTextView(textViewLogin);
        addTextWatcherEditText(editTextLogin);
        addTextWatcherTextView(textViewSex);
        addTextWatcherEditText(editTextDateOfBirth);
        addTextWatcherTextView(textViewHeight);
        addTextWatcherEditText(editTextHeight);
        addTextWatcherTextView(textViewWeight);
        addTextWatcherEditText(editTextWeight);

        editTextDateOfBirth.setOnClickListener(v -> showDatePicker());

        setupPhysicalActivityLevelDropdown();
        setupGoalDropdown();

        imageButtonQuestion.setOnClickListener(v -> showPhysicalActivityInfoDialog());

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
                    editTextDateOfBirth.setText(selectedDay + "." + (selectedMonth + 1) + "." + selectedYear);
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



}