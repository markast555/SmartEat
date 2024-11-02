package com.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import classes.MailRuEmailService;

public class EmailVerificationActivity extends AppCompatActivity {

    private String verificationCode;
    private String userEmail;
    private MailRuEmailService emailService;
    private EditText emailCodeInput;
    private Button verifyEmailButton, resendCodeButton;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_verification_frame);

        emailCodeInput = findViewById(R.id.email_code_input);
        verifyEmailButton = findViewById(R.id.verify_email_button);
        resendCodeButton = findViewById(R.id.resend_code_button);

        userEmail = getIntent().getStringExtra("userEmail");
        emailService = new MailRuEmailService("smarteat@mail.ru", "rX9XvCHCumaYbL0C4vsJ");

        // Генерация и отправка кода при запуске активности
        generateAndSendCode();

        verifyEmailButton.setOnClickListener(v -> verifyCode());
        resendCodeButton.setOnClickListener(v -> {
            resendCodeButton.setEnabled(false); // Отключаем кнопку повторной отправки
            generateAndSendCode();
        });
    }

    private void generateAndSendCode() {
        verificationCode = String.format("%06d", new Random().nextInt(999999));
        emailService.sendVerificationEmail(userEmail, verificationCode);
        startResendTimer();
    }

    private void startResendTimer() {
        // Таймер на 2 минуты
        timer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                resendCodeButton.setText("Повторная отправка через " + millisUntilFinished / 1000 + " сек");
            }

            public void onFinish() {
                resendCodeButton.setText("Отправить код повторно");
                resendCodeButton.setEnabled(true);
            }
        }.start();
    }

    private void verifyCode() {
        String enteredCode = emailCodeInput.getText().toString();
        if (enteredCode.equals(verificationCode)) {
            Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EmailVerificationActivity.this, EntranceActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Неверный код. Проверьте email или попробуйте еще раз.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
