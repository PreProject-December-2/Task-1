package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY ,
                name VARCHAR(128) NOT NULL ,
                last_name VARCHAR(128) NOT NULL ,
                age INT
                );
                """;
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            System.out.println("таблица создана");
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка, таблица не создана");

        }
    }

    public void dropUsersTable() {
        String sql = """
                DROP TABLE users;
                        
                """;
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            System.out.println("таблица удалена");
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка, таблица не удалена");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO users(name,last_name,age)
                VALUES (?,?,?);
                """;
        try (Connection connection = Util.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User " + name + " добавлен в таблицу");
        } catch (SQLException e) {
            System.out.println("Ошибка, user не добавлен в таблицу");
        }
    }

    public void removeUserById(long id) {
        String sql = """
                DELETE FROM users
                WHERE id =?
                """;
        try (Connection connection = Util.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int idDelet = statement.executeUpdate();
            if (idDelet > 0) {
                System.out.println("Пользователь с id " + id + " удален");
            } else {
                System.out.println("такого id " + id + " не существует");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка в удалении пользователя");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = """
                SELECT name,last_name, age
                FROM users
                               
                """;
        try (Connection connection = Util.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                list.add(user);
            }
            for (User x : list) {
                System.out.println(x);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка, пользователи не найдены");
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = """
                DELETE FROM users
                               
                """;
        try (Connection connection = Util.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            System.out.println("Все пользователи User были удалены из таблицы");
        } catch (SQLException e) {
            System.out.println("Ошибка, пользователи не были удалены");
        }
    }
    }

