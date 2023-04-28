package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


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
        session.getTransaction().begin();
        Query<User> query = session.createNativeQuery(
                "CREATE TABLE IF NOT EXISTS users " +
                        "(id INT PRIMARY KEY AUTO_INCREMENT," +
                        " name VARCHAR(50)," +
                        " lastName VARCHAR(50)," +
                        " age INT(3))", User.class);

        query.executeUpdate();
        session.getTransaction().commit();
        LOGGER.log(Level.INFO, "Successful created table \"users\".");
    }

    @Override
    public void dropUsersTable() {
        session.getTransaction().begin();


        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session.getTransaction().begin();
        session.persist(new User(name, lastName, age));
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        session.getTransaction().begin();


        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        session.getTransaction().begin();


        session.getTransaction().commit();
        return null;
    }

    @Override
    public void cleanUsersTable() {
        session.getTransaction().begin();


        session.getTransaction().commit();
    }

    @Override
    public void connectionClose() {
        session.close();
        Util.close();
    }
}
