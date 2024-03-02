package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Configuration configuration = new Configuration().addAnnotatedClass(User.class);
    private final SessionFactory factory = configuration.buildSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
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
            statement.execute(sql);
            System.out.println("таблица создана");
        } catch (SQLException e) {
            System.out.println("Ошибка, таблица не создана");

        }
    }

    @Override
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

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("user с именем " + name + " добавлен в таблицу");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Возникла ошибка при добавлении user в таблицу");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            System.out.println(user);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Возникла ошибка при удалении user id");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            list = session.createQuery("FROM User").getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Возникла ошибка при получении всех user");
        }
        for (User x : list) {
            System.out.println(x);
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Возникла ошибка при удалении всех пользователей");
        }
    }
}
