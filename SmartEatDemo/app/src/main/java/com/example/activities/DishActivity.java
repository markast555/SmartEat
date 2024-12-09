package com.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.SQLException;

import classes.DatabaseParams;
import classes.Diary;
import classes.Dish;
import classes.User;
import classes.UserRepositoryCrud;

public class DishActivity extends AppCompatActivity {
    private User user = null;
    private Dish dish = null;
    boolean isRecordDish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dish_frame);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user = getIntent().getParcelableExtra("user");
        dish = getIntent().getParcelableExtra("dish");
        isRecordDish = getIntent().getBooleanExtra("isRecordDish", false);
        if (!isRecordDish){
            Button buttonSave = findViewById(R.id.buttonSave);
            buttonSave.setText("К дневнику");
        }

        ((TextView)findViewById(R.id.textViewDishName)).setText(dish.getName());
        ((TextView)findViewById(R.id.textViewCaloriesCount)).setText(String.valueOf(dish.getCalorieContent()));
        ((TextView)findViewById(R.id.textViewEatingName)).setText(dish.getMealType().getType());
        if (dish.getDescription() != null){
            ((TextView)findViewById(R.id.textViewDescriptionText)).setText(dish.getDescription());
        }else{
            ((TextView)findViewById(R.id.textViewDescriptionText)).setText("Нет описания блюда");
        }


        setupUI();
    }

    private void setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton imageButtonHome = findViewById(R.id.imageButtonHome);
        imageButtonHome.setOnClickListener(v -> {
            Intent intent = null;
            if (isRecordDish){
                intent = new Intent(DishActivity.this, Main2Activity.class);
            }else{
                intent = new Intent(DishActivity.this, MainActivity.class);
            }
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });

        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(v -> {
            Intent intent = null;
            if (isRecordDish){
                intent = new Intent(DishActivity.this, ManualSearchResultsActivity.class);
            }else{
                intent = new Intent(DishActivity.this, DiaryActivity.class);
            }
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });


        ImageButton imageButtonDiary = findViewById(R.id.imageButtonDiary);
        imageButtonDiary.setOnClickListener(v -> {
            Intent intent = new Intent(DishActivity.this, DiaryActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity

        });

        ImageButton imageButtonProfile = findViewById(R.id.imageButtonProfile);
        imageButtonProfile.setOnClickListener(v -> {
            Intent intent = new Intent(DishActivity.this, ProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });
    }

    public void clickOnMainButton(View view) {
        if (isRecordDish) {
            Diary diary = new Diary();
            diary.setIdUser(user.getIdUser());
            diary.setDish(dish);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserRepositoryCrud userRepositoryCrud = new UserRepositoryCrud();
                    userRepositoryCrud.setConnectionParameters(DatabaseParams.getUrl(), DatabaseParams.getUser(), DatabaseParams.getPassword());
                    boolean isCreateRecord = false;
                    Dish dish = null;
                    try {
                        isCreateRecord = userRepositoryCrud.createRecordingInDiary(diary);
                    } catch (SQLException e) {
                        System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
                        showInfo("Ощибка соединения");
                    } catch (ClassNotFoundException e) {
                        System.out.println("Драйвер базы данных не найден: " + e.getMessage());
                        showInfo("Ощибка соединения");
                    }

                    if (isCreateRecord) {
                        showInfo("Блюдо добавлено в дневник");
                    }else{
                        showInfo("Блюдо не удалось добавить в дневник");
                    }
                    Intent intent = new Intent(DishActivity.this, ManualSearchResultsActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }).start();
        }else{
            Intent intent = new Intent(DishActivity.this, DiaryActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }

    private void showInfo(String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DishActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }
}