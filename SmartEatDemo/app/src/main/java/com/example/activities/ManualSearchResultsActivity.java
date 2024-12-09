package com.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.SQLException;
import java.util.ArrayList;

import classes.DatabaseParams;
import classes.Dish;
import classes.DishAdapter;
import classes.User;
import classes.UserRepositoryCrud;

public class ManualSearchResultsActivity extends AppCompatActivity {

    private EditText editTextEntranceNameOfDish;
    private ListView dishesList;
    private User user = null;
    private ArrayList<Dish> dishes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        user = getIntent().getParcelableExtra("user");

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.manual_search_results_frame);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dishesList = findViewById(R.id.listViewDishes);

        dishesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Вы выбрали: " + dishes.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });


        setupUI();
    }

    private void setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextEntranceNameOfDish = findViewById(R.id.editTextEntranceNameOfDish);
        addTextWatcherEditText(editTextEntranceNameOfDish);

        ImageButton imageButtonHome = findViewById(R.id.imageButtonHome);
        imageButtonHome.setOnClickListener(v -> navigateToMainActivity());

        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(v -> navigateToMainActivity());

        ImageButton imageButtonDiary = findViewById(R.id.imageButtonDiary);
        imageButtonDiary.setOnClickListener(v -> {
            Intent intent = new Intent(ManualSearchResultsActivity.this, DiaryActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });

        ImageButton imageButtonProfile = findViewById(R.id.imageButtonProfile);
        imageButtonProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ManualSearchResultsActivity.this, ProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(ManualSearchResultsActivity.this, Main2Activity.class);
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

    public void clickOnSearch(View v){
        System.out.println("editTextEntranceNameOfDish = " + editTextEntranceNameOfDish.getText());

        String name = editTextEntranceNameOfDish.getText().toString();
        dishes.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepositoryCrud userRepositoryCrud = new UserRepositoryCrud();
                userRepositoryCrud.setConnectionParameters(DatabaseParams.getUrl(), DatabaseParams.getUser(), DatabaseParams.getPassword());
                Dish dish = null;
                try {
                    dish = userRepositoryCrud.findDishByName(name);
                } catch (SQLException e) {
                    System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
                    showInfo("Ощибка соединения");
                } catch (ClassNotFoundException e) {
                    System.out.println("Драйвер базы данных не найден: " + e.getMessage());
                    showInfo("Ощибка соединения");
                }

                if (dish != null) {
                    System.out.println("Блюдо нашлось");
                    System.out.println(dish.toString());
                    dishes.add(dish);

                    // Инициализация адаптера и установка его в ListView
                    runOnUiThread(() -> {
                        DishAdapter dishAdapter = new DishAdapter(ManualSearchResultsActivity.this, dishes, user);
                        dishesList.setAdapter(dishAdapter); // Устанавливаем адаптер
                    });
                }else{
                    System.out.println("Блюдо не найдено");
                    showInfo("Блюдо не найдено");
                }
            }
        }).start();
    }

    private void showInfo(String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ManualSearchResultsActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }
}