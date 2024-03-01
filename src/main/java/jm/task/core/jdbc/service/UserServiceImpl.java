package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao jdbc = new UserDaoJDBCImpl();

    public void createUsersTable() {
        jdbc.createUsersTable();
    }

    public void dropUsersTable() {
        jdbc.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        jdbc.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        jdbc.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return jdbc.getAllUsers();
    }

    public void cleanUsersTable() {
        jdbc.cleanUsersTable();
    }
}
