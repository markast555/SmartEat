package classes;


public class DatabaseParams {

    static private String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7743003?useUnicode=true&characterEncoding=utf8";
    static private String user = "sql7743003"; // Имя пользователя //sql7743003
    static private String password = "tRSFdjQvdz"; // Ваш пароль

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