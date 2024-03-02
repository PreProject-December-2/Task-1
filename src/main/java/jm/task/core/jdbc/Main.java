package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Svetik", "Semitsvetik", (byte) 32);
        service.saveUser("Gruzik", "Pinacolada", (byte) 12);
        service.saveUser("Anton", "Kirienko", (byte) 74);
        service.saveUser("Katerina", "Pinacolada", (byte) 27);
        service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
