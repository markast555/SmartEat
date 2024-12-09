package com.example.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import classes.CustomThumbDrawable;
import classes.User;

public class Main2Activity extends AppCompatActivity {

    private SeekBar seekBar;
    private CustomThumbDrawable customThumb;
    private ImageButton imageButtonProfile;
    private User user = null;

    public Drawable createProgressDrawable(int height, int trackWidth, Context context) {
        // Создаем закругленные углы
        float[] radii = new float[]{
                height / 2f, height / 2f, // Верхние углы
                height / 2f, height / 2f, // Нижние углы
                height / 2f, height / 2f,
                height / 2f, height / 2f
        };

        // Создаем фон прогресс-бара
        ShapeDrawable backgroundDrawable = new ShapeDrawable();
        backgroundDrawable.setShape(new RoundRectShape(radii, null, null));
        backgroundDrawable.getPaint().setColor(ContextCompat.getColor(context, R.color.gray)); // Замените на нужный цвет

        // Создаем полосу прогресса
        ShapeDrawable progressDrawable = new ShapeDrawable();
        progressDrawable.setShape(new RoundRectShape(radii, null, null));
        progressDrawable.getPaint().setColor(ContextCompat.getColor(context, R.color.white)); // Замените на нужный цвет

        // Оборачиваем полосу прогресса в ClipDrawable
        ClipDrawable clipDrawable = new ClipDrawable(progressDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);

        // Объединяем фон и полосу прогресса в LayerDrawable
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{backgroundDrawable, clipDrawable});
        layerDrawable.setId(0, android.R.id.background);
        layerDrawable.setId(1, android.R.id.progress);

        // Устанавливаем ширину LayerDrawable
        layerDrawable.setBounds(0, 0, trackWidth, height);

        return layerDrawable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        user = getIntent().getParcelableExtra("user");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2_frame); // Замените на ваш файл макета

        seekBar = findViewById(R.id.sliderSwitch);

        // Получаем высоту и ширину ползунка из ресурсов
        int thumbHeight = (int) getResources().getDimension(R.dimen.seekbar_thumb_height);
        int thumbWidth = (int) getResources().getDimension(R.dimen.seekbar_thumb_width);
        int trackWidth = thumbWidth * 2;

        // Создаем и устанавливаем прогресс-бара
        Drawable progressDrawable = createProgressDrawable(thumbHeight, trackWidth, this);
        seekBar.setProgressDrawable(progressDrawable);

        int lightGrayColor = ContextCompat.getColor(this, R.color.light_gray);

        // Создаем и устанавливаем кастомный ползунок
        customThumb = new CustomThumbDrawable(this, "Ручной", lightGrayColor);
        seekBar.setThumb(customThumb);
        seekBar.setThumbOffset(0);

        // Устанавливаем прогресс в 100
        seekBar.setProgress(100);

        imageButtonProfile = findViewById(R.id.imageButtonProfile);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileActivity();
            }
        });

        // Устанавливаем слушатель изменений
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private boolean isManual = true; // Начальное состояние - "Ручной"

            private void handleProgressChanged(int progress) {
                if (isManual && progress == 0) {
                    isManual = false;
                    customThumb.setText("Авто");
                    switchToMainActivity();
                } else if (!isManual && progress >= 50) {
                    isManual = true;
                    customThumb.setText("Ручной");
                }
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Реагируем только на пользовательские действия
                if (fromUser) {
                    handleProgressChanged(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Действия при начале перемещения ползунка (если необходимо)
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if (progress < 50) {
                    seekBar.setProgress(0);
                    handleProgressChanged(0);
                } else {
                    seekBar.setProgress(100);
                    handleProgressChanged(100);
                }
            }

        });

        Button buttonProceedToSelection = findViewById(R.id.buttonProceedToSelection);
        buttonProceedToSelection.setOnClickListener(v -> {
            Intent intent = new Intent(Main2Activity.this, ManualSearchResultsActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });

        ImageButton imageButtonDiary = findViewById(R.id.imageButtonDiary);
        imageButtonDiary.setOnClickListener(v -> {
            Intent intent = new Intent(Main2Activity.this, DiaryActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(0, 0); // Отключает анимацию перехода
            finish(); // Завершает ProfileActivity
        });
    }


    private void openProfileActivity() {
        Intent intent = new Intent(Main2Activity.this, ProfileActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        overridePendingTransition(0, 0); // Убираем анимацию перехода
    }


    private void switchToMainActivity() {
        // Меняем цвета элементов интерфейса, если необходимо
        // Например, меняем цвет фона
        // findViewById(R.id.main2_frame_layout).setBackgroundColor(ContextCompat.getColor(this, R.color.original_background_color));

        // Запускаем MainActivity
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        overridePendingTransition(0, 0);

        // Закрываем Main2Activity
        finish();
    }
}
