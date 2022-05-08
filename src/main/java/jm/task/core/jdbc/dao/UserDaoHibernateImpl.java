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

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction trSaveUser = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS user (id INT NOT NULL AUTO_INCREMENT,name VARCHAR(45) NOT NULL," +
                    "lastName VARCHAR(45) NOT NULL,age INT NOT NULL, PRIMARY KEY(id))").executeUpdate();
            trSaveUser.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction trDropUsTable = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS user").executeUpdate();
            trDropUsTable.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction trSaveUser = session.beginTransaction();
            session.save(new User(name, lastName,age));
            trSaveUser.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction trRemove = session.beginTransaction();
            session.delete(session.get(User.class, id));
            trRemove.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction trGetUser = session.getTransaction();
        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);
        List <User> list = session.createQuery(criteriaQuery).getResultList();
        try {
            trGetUser.commit();
            return list;
        } catch (HibernateException e) {
            e.printStackTrace();
            trGetUser.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction trCleanTable = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
            trCleanTable.commit();
        }   catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
