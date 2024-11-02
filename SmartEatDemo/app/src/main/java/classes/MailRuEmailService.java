package classes;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailRuEmailService {
    private static final String SMTP_SERVER = "smtp.mail.ru";
    private static final String SMTP_PORT = "465"; // Используем SSL

    private final String username; // Ваш email на Mail.ru
    private final String password; // Пароль или пароль приложения Mail.ru
    private final ExecutorService executorService;

    public MailRuEmailService(String username, String password) {
        this.username = username;
        this.password = password;
        this.executorService = Executors.newSingleThreadExecutor(); // Инициализируем ExecutorService
    }

    public void sendVerificationEmail(String toEmail, String code) {
        executorService.execute(() -> {
            Properties prop = new Properties();
            prop.put("mail.smtp.host", SMTP_SERVER);
            prop.put("mail.smtp.port", SMTP_PORT);
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.ssl.enable", "true"); // Используем SSL
            prop.put("mail.smtp.socketFactory.port", SMTP_PORT);
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Используем SSL-сокет

            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(toEmail)
                );
                message.setSubject("Код подтверждения");
                message.setText("Ваш код подтверждения: " + code);

                Transport.send(message);

                System.out.println("Email отправлен успешно на " + toEmail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }

    public void shutdown() {
        executorService.shutdown(); // Закрываем ExecutorService при завершении работы
    }
}
