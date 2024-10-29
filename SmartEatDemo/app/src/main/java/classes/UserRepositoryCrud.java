package classes;

import android.os.Build;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Соединение с БД установлено.");
            } catch (SQLException e) {
                System.out.println("Ошибка: " + e.getMessage());
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

    /**
     * Создание записи в БД.
     *
     * @param user - 'заполненный объект'
     * @return сгенерированный UUID новой записи
     */
    //@Override
    public UUID create(User user) {

        UUID uuid = user.getId();

        setConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into public.users_se (id_user, login, password, name, sex, date_of_birth, height, weight, level_of_physical_activity, goals, individual_characters, phone_number) values (?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?) ")) {
            //statement.setObject(1, uuid, java.sql.Types.OTHER);
            statement.setObject(1, user.getId());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSex().getType());
            statement.setDate(6, dateToSqlDate(user.getDateOfBirth()));
            statement.setInt(7, user.getHeight());
            statement.setFloat(8, user.getWeight());
            statement.setString(9, user.getPhysicalActivityLevel().getType());
            statement.setString(10, user.getGoals().getType());
            statement.setString(11, user.getIndividualCharacteristics());
            statement.setString(12, user.getPhoneNumber());

            statement.execute();
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения: " + e.getMessage());
            uuid = null;
        }
        closeConnection();

        return uuid;
    }

    //@Override
    public User selectById(UUID id) {
        User user = null;

        setConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select id_user, login, password, name, sex, date_of_birth, height, weight, level_of_physical_activity, goals, individual_characters, phone_number from public.user_se where id_user = ?")) {
            statement.setObject(1, id, Types.OTHER);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        user = new User((UUID) resultSet.getObject("id_user"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getString("name"),
                                Sex.fromType(resultSet.getString("sex")),
                                resultSet.getDate("date_of_birth").toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate(),
                                resultSet.getInt("height"),
                                resultSet.getFloat("weight"),
                                resultSet.getString("phone_number"),
                                PhysicalActivityLevel.fromType(resultSet.getString("level_of_physical_activity")),
                                Goals.fromType(resultSet.getString("goals")),
                                resultSet.getString("individual_characters"));
                    }
                } else {
                    System.out.println("Данные не найдены");
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения: " + e.getMessage());
        }
        closeConnection();

        return user;
    }

    //@Override
    public List<User> selectAll() {
        List<User> userList = new ArrayList<>();

        setConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "select id, firstname, lastname, birthdate, isgraduated from public.user_se")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        userList.add(new User((UUID) resultSet.getObject("id_user"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getString("name"),
                                Sex.fromType(resultSet.getString("sex")),
                                resultSet.getDate("date_of_birth").toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate(),
                                resultSet.getInt("height"),
                                resultSet.getFloat("weight"),
                                resultSet.getString("phone_number"),
                                PhysicalActivityLevel.fromType(resultSet.getString("level_of_physical_activity")),
                                Goals.fromType(resultSet.getString("goals")),
                                resultSet.getString("individual_characters")));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения: " + e.getMessage());
        }
        closeConnection();

        return userList;
    }

    //@Override
    public int update(User user) {
        int result = 0;

        setConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "update public.user_se set login = ?, password = ?, name = ?, sex = ?, date_of_birth = ?, height = ?, weight = ?, level_of_physical_activity = ?, goals = ?, individual_characters = ?, phone_number = ?, isgraduated = ? where id = ?")) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSex().getType());
            statement.setDate(5, dateToSqlDate(user.getDateOfBirth()));
            statement.setInt(6, user.getHeight());
            statement.setFloat(7, user.getWeight());
            statement.setString(8, user.getPhysicalActivityLevel().getType());
            statement.setString(9, user.getGoals().getType());
            statement.setString(10, user.getIndividualCharacteristics());
            statement.setString(11, user.getPhoneNumber());

            statement.setObject(12, user.getId(), java.sql.Types.OTHER);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения: " + e.getMessage());
        }
        closeConnection();

        return result;
    }

    //@Override
    public int remove(List<UUID> idList) {
        int result = 0;

        String listId = idList.stream()
                .map(Object::toString)
                .collect(Collectors.joining("', '", "'", "'"));

        setConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from public.person where id in (" + listId + ")")) {
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка выполнения: " + e.getMessage());
        }
        closeConnection();

        return result;
    }
}