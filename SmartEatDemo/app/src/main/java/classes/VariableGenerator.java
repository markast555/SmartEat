package classes;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.Random;
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

    private static final String[] NAMES = {
            "john", "jane", "mike", "sara", "alex", "emma", "david", "lisa", "chris", "kathy"
    };

    private static final String[] SURNAMES = {
            "smith", "johnson", "williams", "brown", "jones", "garcia", "miller", "davis", "rodriguez", "martinez"
    };

    private static final Random RANDOM = new Random();


    public static String generateRandomGmail() {
        String name = NAMES[RANDOM.nextInt(NAMES.length)];
        String surname = SURNAMES[RANDOM.nextInt(SURNAMES.length)];
        int randomNumber = RANDOM.nextInt(100); // Случайное число от 0 до 99
        return name + "." + surname + randomNumber + "@gmail.com";
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
