package classes;


public class DatabaseParams {

    static private String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7741688"; // Замените your-database на имя вашей базы данных
    static private String user = "sql7741688"; // Имя пользователя
    static private String password = "lY3YIcUgkp"; // Ваш пароль

    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    private static User user_se = null;

    public static User getUser_se() {
        return user_se;
    }

    public static void setUser_se(User user_se) {
        DatabaseParams.user_se = user_se;
    }
}
