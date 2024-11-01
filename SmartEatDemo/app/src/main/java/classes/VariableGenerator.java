package classes;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.UUID;

public class VariableGenerator {

    //Метод генирации логина
    public static String generateRandomLogin() {

        int min = 4, max = 16;
        int length = (new SecureRandom()).nextInt(max - min + 1) + min;
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";// Длина логина

        SecureRandom random = new SecureRandom();
        StringBuilder login = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            login.append(CHARACTERS.charAt(index));
        }

        return login.toString();
    }

    //Метод генирации номера телефона
    public static String generateRandomPhoneNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder phoneNumber = new StringBuilder("+7-");

        for (int i = 0; i < 3; i++) {
            phoneNumber.append(random.nextInt(10)); // Генерация цифры от 0 до 9
        }
        phoneNumber.append("-");

        // Первая часть номера (3 цифры)
        for (int i = 0; i < 3; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        phoneNumber.append("-");

        // Вторая часть номера (2 цифры)
        for (int i = 0; i < 2; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        phoneNumber.append("-");

        // Третья часть номера (2 цифры)
        for (int i = 0; i < 2; i++) {
            phoneNumber.append(random.nextInt(10));
        }

        return phoneNumber.toString();
    }

    //Метод генерации UUID
    public static UUID getUid() {
        return UUID.randomUUID();
    }

    // Метод для хэширования пароля
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    //Метод для сравнения введенного пароля с хэшем
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

}
