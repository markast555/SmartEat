package com.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.SQLException;
import java.util.ArrayList;

import classes.DatabaseParams;
import classes.Diary;
import classes.DiaryDishAdapter;
import classes.User;
import classes.UserRepositoryCrud;


public class DiaryActivity extends AppCompatActivity {
    private ListView dishesList;
    private TextView textViewDailyCalorieIntakeCount;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getIntent().getParcelableExtra("user");
        System.out.println(user.toString());

        EdgeToEdge.enable(this);
        setContentView(R.layout.diary_frame);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final ArrayList<Diary>[] dishes = new ArrayList[]{new ArrayList<>()};
        dishesList = findViewById(R.id.listViewDishes); // Инициализация списка здесь

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepositoryCrud userRepositoryCrud = new UserRepositoryCrud();
                userRepositoryCrud.setConnectionParameters(DatabaseParams.getUrl(), DatabaseParams.getUser(), DatabaseParams.getPassword());
                try {
                    dishes[0] = userRepositoryCrud.findRecordingsInDiaryByUserId(user.getIdUser());

                    // Обновление UI на главном потоке
                    runOnUiThread(() -> {
                        if (!dishes[0].isEmpty()) {
                            DiaryDishAdapter diaryDishAdapter = new DiaryDishAdapter(DiaryActivity.this, dishes[0], user);
                            dishesList.setAdapter(diaryDishAdapter); // Устанавливаем адаптер

                            dishesList.setOnItemClickListener((parent, view, position, id) -> {
                                Toast.makeText(getApplicationContext(), "Даты: " + dishes[0].get(position).getEatingTime().toString(), Toast.LENGTH_SHORT).show();
                            });
                        }
                    });

                } catch (SQLException e) {
                    System.err.println("Не удалось получить записи из базы данных: " + e.getMessage());
                    showInfo("Ошибка получения данных");
                } catch (ClassNotFoundException e) {
                    System.err.println("Класс не найден при получении записей: " + e.getMessage());
                    showInfo("Ошибка получения данных");
                } catch (Exception e) {
                    System.err.println("Произошла ошибка: " + e.getMessage());
                    showInfo("Ошибка получения данных");
                }
            }
        }).start();

        setupUI();
    }


    private void setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Инициализация TextView
        textViewDailyCalorieIntakeCount = findViewById(R.id.textViewDailyCalorieIntakeCount);

        // Получение объекта User из Intent
        user = getIntent().getParcelableExtra("user");

        // Проверка на null и изменение текста
        if (user != null) {
            // Изменение текста TextView
            textViewDailyCalorieIntakeCount.setText(user.getCalorieNorm() + " ккал");
        } else {
            textViewDailyCalorieIntakeCount.setText("Данные не доступны");
        }

        ImageButton imageButtonHome = findViewById(R.id.imageButtonHome);
        imageButtonHome.setOnClickListener(v -> {
            Intent intent = new Intent(DiaryActivity.this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });

        ImageButton imageButtonProfile = findViewById(R.id.imageButtonProfile);
        imageButtonProfile.setOnClickListener(v -> {
            Intent intent = new Intent(DiaryActivity.this, ProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });
    }

    private void showInfo(String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DiaryActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }
}