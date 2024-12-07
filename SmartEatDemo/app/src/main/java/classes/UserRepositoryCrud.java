package classes;

//import java.sql.SQType;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * Класс операций с БД.
 */

public class UserRepositoryCrud{

    private String url;
    private String user;
    private String password;

    private Connection connection = null;

    private boolean isConnection = false;

    public boolean isConnection() {
        return isConnection;
    }

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


    public void setConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Соединение установлено успешно!");
                isConnection = true;
            } catch (SQLException e) {
                throw new SQLException("Ошибка подключения: " + e.getMessage(), e);
            } catch (ClassNotFoundException e) {
                throw new ClassNotFoundException("Драйвер не найден: " + e.getMessage(), e);
            }
        } else {
            System.out.println("Соединение уже установлено.");
        }
    }

    /**
     * Закрывает соединение с БД.
     */
    public void closeConnection() throws SQLException {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Соединение прервано успешно!");
            } catch (SQLException e) {
                throw new SQLException("Ошибка подключения: " + e.getMessage(), e);
            }
        }
    }

    public static UUID getUid() {
        return UUID.randomUUID();
    }

    public static java.sql.Date dateToSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(String.valueOf(localDate)); //Исправлено
    }


    public boolean create(User user) throws SQLException, ClassNotFoundException {
        boolean result = false;
        user.setCalorieNorm(user.countCalorieNorm());
        setConnection();
        try {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (id_user, login, password, sex, date_of_birth, height, weight, level_of_physical_activity, goal, gmail, calorie_norm) VALUES (?,?,?,?,?,?,?,?,?,?,?)")) {


                statement.setObject(1, user.getIdUser().toString());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getSex().getType());
                statement.setDate(5, dateToSqlDate(user.getDateOfBirth()));
                statement.setInt(6, user.getHeight());
                statement.setFloat(7, user.getWeight());
                statement.setString(8, user.getLevelOfPhysicalActivity().getType());
                statement.setString(9, user.getGoals().getType());
                statement.setString(10, user.getGmail());
                statement.setInt(11, user.getCalorieNorm());

                statement.execute();
                result = true;
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка подключения: " + e.getMessage(), e);
        } finally {
            closeConnection(); // Закрываем соединение в блоке finally
        }

        return result;
    }


    public boolean checkByOneField(String value, String nameField) throws SQLException, ClassNotFoundException {
        boolean result = false;

        setConnection(); // Установите соединение с базой данных

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id_user, login, password, sex, date_of_birth, height, weight, level_of_physical_activity, goal, gmail, calorie_norm FROM users WHERE " + nameField + " = ?")) {

            // Устанавливаем значение параметра
            statement.setString(1, value);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = true;
                } else {
                    System.out.println("Данные не найдены");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка подключения: " + e.getMessage(), e);
        } finally {
            closeConnection(); // Закрываем соединение в блоке finally
        }

        return result;
    }

    public int update(User user) throws SQLException, ClassNotFoundException {
        int result = 0;
        user.setCalorieNorm(user.countCalorieNorm());
        setConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET login = ?, password = ?, sex = ?, date_of_birth = ?, height = ?, weight = ?, level_of_physical_activity = ?, goal = ?, gmail = ?, colorie_norm = ? WHERE users.id_user = ?")) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getSex().getType());
            statement.setDate(4, dateToSqlDate(user.getDateOfBirth()));
            statement.setInt(5, user.getHeight());
            statement.setFloat(6, user.getWeight());
            statement.setString(7, user.getLevelOfPhysicalActivity().getType());
            statement.setString(8, user.getGoals().getType());
            statement.setString(9, user.getGmail());
            statement.setInt(10, user.getCalorieNorm());
            statement.setString(11, user.getIdUser().toString());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Ошибка выполнения: " + e.getMessage(), e);
        } finally {
            closeConnection(); // Закрываем соединение в блоке finally
        }

        return result;
    }


    public boolean createDish(Dish dish) throws SQLException, ClassNotFoundException {
        boolean result = false;

        setConnection();
        try {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO dishes (id_dish, name, calorie_content, meal_type, description) VALUES (?,?,?,?,?)")) {

                statement.setString(1, dish.getIdDish().toString());
                statement.setString(2, dish.getName());
                statement.setInt(3, dish.getCalorieContent());
                statement.setString(4, dish.getMealType().getType());
                statement.setString(5, dish.getDescription());

                statement.execute();
                result = true;
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка подключения: " + e.getMessage(), e);
        } finally {
            closeConnection(); // Закрываем соединение в блоке finally
        }

        return result;
    }

    public Dish findDishByName(String name) throws SQLException, ClassNotFoundException {
        Dish dish = null;

        setConnection(); // Установите соединение с базой данных

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id_dish, name, meal_type, calorie_content, description FROM dishes WHERE name = ?")) {

            // Устанавливаем значение параметра
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    dish = new Dish(UUID.fromString(resultSet.getString("id_dish")),
                            resultSet.getString("name"),
                            MealType.fromType(resultSet.getString("meal_type")),
                            resultSet.getInt("calorie_content"),
                            resultSet.getString("description"));
                } else {
                    System.out.println("Данные не найдены");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка подключения: " + e.getMessage(), e);
        } finally {
            closeConnection(); // Закрываем соединение в блоке finally
        }

        return dish;
    }

    public Dish findDishByID(UUID dishId) throws SQLException, ClassNotFoundException {
        Dish dish = null;

        setConnection(); // Установите соединение с базой данных

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id_dish, name, meal_type, calorie_content, description FROM dishes WHERE id_dish = ?")) {

            // Устанавливаем значение параметра
            statement.setString(1, dishId.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    dish = new Dish(UUID.fromString(resultSet.getString("id_dish")),
                            resultSet.getString("name"),
                            MealType.fromType(resultSet.getString("meal_type")),
                            resultSet.getInt("calorie_content"),
                            resultSet.getString("description"));
                } else {
                    System.out.println("Данные не найдены");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка подключения: " + e.getMessage(), e);
        } finally {
            closeConnection(); // Закрываем соединение в блоке finally
        }

        return dish;
    }

    public boolean createRecordingInDiary(Diary diary) throws SQLException, ClassNotFoundException {
        boolean result = false;

        setConnection();
        try {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO diary (id_diary, id_user, id_dish, eating_time) VALUES (?,?,?,?)")) {

                statement.setString(1, diary.getIdDiary().toString());
                statement.setString(2, diary.getIdUser().toString());
                statement.setString(3, diary.getIdDish().toString());
                statement.setTimestamp(4, toSqlTimestamp(diary.getEatingTime()));

                statement.execute();
                result = true;
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка подключения: " + e.getMessage(), e);
        } finally {
            closeConnection(); // Закрываем соединение в блоке finally
        }

        return result;
    }

    public User selectByLogin(String login) throws SQLException, ClassNotFoundException {
        User user = null;

        setConnection(); // Установите соединение с базой данных

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id_user, login, password, sex, date_of_birth, height, weight, level_of_physical_activity, goal, gmail, calorie_norm FROM users WHERE login = ?")) {

            // Устанавливаем значение параметра
            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(UUID.fromString(resultSet.getString("id_user")),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            Sex.fromType(resultSet.getString("sex")),
                            toLocalDate(resultSet.getDate("date_of_birth")),
                            resultSet.getInt("height"),
                            resultSet.getFloat("weight"),
                            PhysicalActivityLevel.fromType(resultSet.getString("level_of_physical_activity")),
                            Goals.fromType(resultSet.getString("goal")),
                            resultSet.getString("gmail"),
                            resultSet.getInt("calorie_norm"));
                } else {
                    System.out.println("Данные не найдены");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка подключения: " + e.getMessage(), e);
        } finally {
            closeConnection(); // Закрываем соединение в блоке finally
        }

        return user;
    }

    public List<Diary> findRecordingsInDiaryByUserId(UUID userId) throws SQLException, ClassNotFoundException {
        List<Diary> diaries = new ArrayList<>();

        setConnection(); // Установите соединение с базой данных

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id_diary, id_user, id_dish, eating_time FROM diary WHERE id_user = ?")) {

            // Устанавливаем значение параметра
            statement.setString(1, userId.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Timestamp eatingTime = resultSet.getTimestamp("eating_time");
                    LocalDateTime localDateTime = toLocalDateTime(eatingTime);

                    Diary diary = new Diary(UUID.fromString(resultSet.getString("id_diary")),
                            UUID.fromString(resultSet.getString("id_user")),
                            UUID.fromString(resultSet.getString("id_dish")),
                            localDateTime);
                    diaries.add(diary);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Ошибка подключения: " + e.getMessage(), e);
        } finally {
            closeConnection(); // Закрываем соединение в блоке finally
        }

        return diaries;
    }


    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null; // Обработка случая, когда timestamp равен null
        }

        Instant instant = Instant.ofEpochMilli(timestamp.getTime());
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        return zonedDateTime.toLocalDateTime();
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

    public static Timestamp toSqlTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null; // Обработка случая, когда дата равна null
        }

        return new java.sql.Timestamp(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

}
