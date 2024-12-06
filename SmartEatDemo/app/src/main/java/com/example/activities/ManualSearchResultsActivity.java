package com.example.activities;

import android.content.Intent;
import android.os.Bundle;
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

import classes.DishAdapter;

public class ManualSearchResultsActivity extends AppCompatActivity {

    private ListView dishesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.manual_search_results_frame);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dishesList = findViewById(R.id.listViewDishes);
        //ListView dishesList = (ListView) findViewById(R.id.listViewDishes);

        String[] dishes = {"Буду быть v2", "Пёс v2", "Ёк Ёк v2", "В полупустой хате v2",
                "Лавина v2", "Свиноферма v2", "Из говна и палок v2", "Метамарфоза v2",
                "Стая 1993 v2", "Королева v2", "Живой v2", "Зомби надо хоронить v2",
                "Удочка для охоты v2", "Двойной агент v2", "Спасибо за опыт", "Гибрид",
                "Как у людей"};

        String[] masses = {"300г", "150г", "250г", "100г", "300г", "300г", "50кг", "100г",
                "100г", "450г", "150г", "250г", "100г", "250г", "150г", "250г", "300г"};

        DishAdapter dishAdapter = new DishAdapter(this, dishes);
        dishesList.setAdapter(dishAdapter);

        dishesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), masses[position], Toast.LENGTH_SHORT).show();
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

        ImageButton imageButtonHome = findViewById(R.id.imageButtonHome);
        imageButtonHome.setOnClickListener(v -> {
            Intent intent = new Intent(ManualSearchResultsActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });

        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(v -> {
            Intent intent = new Intent(ManualSearchResultsActivity.this, Main2Activity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });
    }
}