package classes;

import android.annotation.SuppressLint;
import android.os.Build;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Класс операций с БД.
 */

public class UserRepositoryCrud{

    private String url;
    private String user;
    private String password;

    private Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    /**
     * Параметры соединения.
     *
     * @param url      - URL
     * @param user     - пользователь
     * @param password - пароль
     */
    public void setConnectionParameters(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Устанавливает соединение с БД.
     */
    public void setConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Соединение установлено успешно!");
            } catch (SQLException e) {
                System.out.println("Ошибка подключения: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Драйвер не найден: " + e.getMessage());
            }
        } else {
            System.out.println("Соединение уже установлено.");
        }
    }

    /**
     * Закрывает соединение с БД.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Соединение прервано успешно!");
            } catch (SQLException e) {
                System.out.println("Ошибка: Соединение с БД не закрыто");
            }
        }
    }

    public static UUID getUid() {
        return UUID.randomUUID();
    }

    public static java.sql.Date dateToSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(String.valueOf(localDate)); //Исправлено
    }


    public boolean create(User user) {
        boolean result = false;

        setConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into users (id_user, login, password, sex, date_of_birth, height, weight, level_of_physical_activity, goals, phone_number, calorie_norm) values (?,?,?,?,?,?,?,?,?,?,?) ")) {

            statement.setObject(1, user.getIdUser().toString());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getSex().getType());
            statement.setDate(5, dateToSqlDate(user.getDateOfBirth()));
            statement.setInt(6, user.getHeight());
            statement.setFloat(7, user.getWeight());
            statement.setString(8, user.getLevelOfPhysicalActivity().getType());
            statement.setString(9, user.getGoals().getType());
            statement.setString(10, user.getPhoneNumber());
            statement.setInt(11, user.getCalorieNorm());

            statement.execute();

            result = true;
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения: " + e.getMessage());
            //e.printStackTrace();
        } finally {
        closeConnection(); // Закрываем соединение в блоке finally
    }

        return result;
    }


    public User selectByLogin(String login) {
        User user = null;

        setConnection(); // Установите соединение с базой данных

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id_user, login, password, sex, date_of_birth, height, weight, level_of_physical_activity, goals, phone_number, calorie_norm FROM users WHERE login = ?")) {

            // Устанавливаем значение параметра
            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("Я тута!!!");
                if (resultSet.next()) {
                    System.out.println("Я тутаАААА!!!");
                    System.out.println("Нашёл!!!");
                    user = new User(UUID.fromString(resultSet.getString("id_user")),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        Sex.fromType(resultSet.getString("sex")),
                            toLocalDate(resultSet.getDate("date_of_birth")),
                        resultSet.getInt("height"),
                        resultSet.getFloat("weight"),
                        PhysicalActivityLevel.fromType(resultSet.getString("level_of_physical_activity")),
                        Goals.fromType(resultSet.getString("goals")),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("calorie_norm"));
                } else {
                    System.out.println("Данные не найдены");
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения: " + e.getMessage());
        } finally {
            closeConnection(); // Закрываем соединение в блоке finally
        }

        return user;
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null; // Обработка случая, когда дата равна null
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return LocalDate.of(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }



//    // Метод для конвертации java.sql.Date в LocalDate
//    private LocalDate convertSqlDateToLocalDate(Date sqlDate) {
//        String dateString = "02/11/2024"; // Пример даты в формате dd/MM/yyyy
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//
//        try {
//            // Преобразование строки в Date
//            Date date = (Date) formatter.parse(dateString);
//            System.out.println("Преобразованная дата: " + date);
//        } catch (ParseException e) {
//            System.out.println("Ошибка преобразования даты: " + e.getMessage());
//        }
//    }

//
//    //@Override
//    public List<User> selectAll() {
//        List<User> userList = new ArrayList<>();
//
//        setConnection();
//        try (PreparedStatement statement = connection.prepareStatement(
//                "select id, firstname, lastname, birthdate, isgraduated from public.user_se")) {
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        userList.add(new User((UUID) resultSet.getObject("id_user"),
//                                resultSet.getString("login"),
//                                resultSet.getString("password"),
//                                resultSet.getString("name"),
//                                Sex.fromType(resultSet.getString("sex")),
//                                resultSet.getDate("date_of_birth").toInstant()
//                                        .atZone(ZoneId.systemDefault())
//                                        .toLocalDate(),
//                                resultSet.getInt("height"),
//                                resultSet.getFloat("weight"),
//                                resultSet.getString("phone_number"),
//                                PhysicalActivityLevel.fromType(resultSet.getString("level_of_physical_activity")),
//                                Goals.fromType(resultSet.getString("goals")),
//                                resultSet.getString("individual_characters")));
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Ошибка выполнения: " + e.getMessage());
//        }
//        closeConnection();
//
//        return userList;
//    }
//
//    //@Override
//    public int update(User user) {
//        int result = 0;
//
//        setConnection();
//        try (PreparedStatement statement = connection.prepareStatement(
//                "update public.user_se set login = ?, password = ?, name = ?, sex = ?, date_of_birth = ?, height = ?, weight = ?, level_of_physical_activity = ?, goals = ?, individual_characters = ?, phone_number = ?, isgraduated = ? where id = ?")) {
//
//            statement.setString(1, user.getLogin());
//            statement.setString(2, user.getPassword());
//            statement.setString(3, user.getName());
//            statement.setString(4, user.getSex().getType());
//            statement.setDate(5, dateToSqlDate(user.getDateOfBirth()));
//            statement.setInt(6, user.getHeight());
//            statement.setFloat(7, user.getWeight());
//            statement.setString(8, user.getPhysicalActivityLevel().getType());
//            statement.setString(9, user.getGoals().getType());
//            statement.setString(10, user.getIndividualCharacteristics());
//            statement.setString(11, user.getPhoneNumber());
//
//            statement.setObject(12, user.getId(), java.sql.Types.OTHER);
//
//            result = statement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Ошибка выполнения: " + e.getMessage());
//        }
//        closeConnection();
//
//        return result;
//    }
//
//    //@Override
//    public int remove(List<UUID> idList) {
//        int result = 0;
//
//        String listId = idList.stream()
//                .map(Object::toString)
//                .collect(Collectors.joining("', '", "'", "'"));
//
//        setConnection();
//        try (PreparedStatement statement = connection.prepareStatement(
//                "delete from public.person where id in (" + listId + ")")) {
//            result = statement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Ошибка выполнения: " + e.getMessage());
//        }
//        closeConnection();
//
//        return result;
//    }
}