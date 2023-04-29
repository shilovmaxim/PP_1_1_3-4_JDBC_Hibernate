package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private final Session session = Util.getSessionFactory().openSession();
    private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try {
            session.getTransaction().begin();
            session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS users " +
                            "(id INT PRIMARY KEY AUTO_INCREMENT," +
                            " name VARCHAR(50)," +
                            " lastName VARCHAR(50)," +
                            " age INT(3))").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            session.clear();
            LOGGER.log(Level.INFO, "Successful created table \"users\".");
        } catch (Exception e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Failed to create a table.", e);
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session.getTransaction().begin();
            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            session.clear();
            LOGGER.log(Level.INFO, "Successful dropped table \"users\".");
        } catch (Exception e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Failed to drop a table.", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session.getTransaction().begin();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            session.clear();
            LOGGER.log(Level.INFO, "The following data has been successfully saved to the \"users\" table: "
                    + name + " " + lastName + " " + age);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Data saving error.", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.getTransaction().begin();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
            session.clear();
            LOGGER.log(Level.INFO, "Request completed successfully. User from id = " + id + " removed.");
        } catch (Exception e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Request execution error.", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try {
            session.getTransaction().begin();
            usersList = session.createQuery("from User", User.class).list();
            session.getTransaction().commit();
            session.clear();
            LOGGER.log(Level.INFO, "Request completed successfully. The list of users has been received");
        } catch (Exception e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Request execution error.", e);
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session.getTransaction().begin();
//            session.createQuery("delete User").executeUpdate(); // если таблица маленькая
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate(); // если большая мгновенно, но без rollback
            session.getTransaction().commit();
            session.clear();
            LOGGER.log(Level.INFO, "Successful clean table \"users\".");
        } catch (Exception e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Failed to clean a table.", e);
        }
    }

    public void connectionRollback() {
        if (session != null) {
            session.getTransaction().rollback();
        }
        connectionClose();
    }

    @Override
    public void connectionClose() {
        session.close();
        Util.close();
    }
}
