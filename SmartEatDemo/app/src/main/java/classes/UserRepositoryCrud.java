package classes;

//import java.sql.SQType;

import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
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

        setConnection();
        try {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (id_user, login, password, sex, date_of_birth, height, weight, level_of_physical_activity, goal, gmail, calorie_norm) VALUES (?,?,?,?,?,?,?,?,?,?,?)")) {


                statement.setObject(1, user.getIdUser().toString());
                statement.setString(2, user.getLogin());
                statement.setString(3, user.getPassword());
                statement.setString(4, Sex.TranslateFromRusToEng(user.getSex().getType()));
                statement.setDate(5, dateToSqlDate(user.getDateOfBirth()));
                statement.setInt(6, user.getHeight());
                statement.setFloat(7, user.getWeight());
                statement.setString(8, PhysicalActivityLevel.TranslateFromRusToEng(user.getLevelOfPhysicalActivity().getType()));
                statement.setString(9, Goals.TranslateFromRusToEng(user.getGoals().getType()));
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
                            Sex.fromType(Sex.TranslateFromEngToRus(resultSet.getString("sex"))),
                            toLocalDate(resultSet.getDate("date_of_birth")),
                            resultSet.getInt("height"),
                            resultSet.getFloat("weight"),
                            PhysicalActivityLevel.fromType(PhysicalActivityLevel.TranslateFromEngToRus(resultSet.getString("level_of_physical_activity"))),
                            Goals.fromType(Goals.TranslateFromEngToRus(resultSet.getString("goal"))),
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