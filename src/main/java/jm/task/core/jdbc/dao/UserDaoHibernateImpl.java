package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private Session session = null;
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            session.save(new User(name, lastName, age));
            session.beginTransaction();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            session.delete(session.get(User.class, id));
            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        session = sessionFactory.openSession();
        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);
        transaction = session.beginTransaction();
        List<User> userList = session.createQuery(criteriaQuery).getResultList();
        try {
            transaction.commit();
            return userList;
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        try {
            session.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }
}
