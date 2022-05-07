package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction trCreateTable = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT," +
                    "  `Name` VARCHAR(45) NOT NULL," +
                    "  `LastName` VARCHAR(45) NOT NULL," +
                    "  `Age` TINYINT NOT NULL," +
                    "  PRIMARY KEY (`id`))");
            trCreateTable.commit();

        } catch (SessionException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction trDropTable = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user");
            trDropTable.commit();
        } catch (SessionException e) {
            throw new RuntimeException(e);

        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction trSaveUser = session.beginTransaction();
            session.save(name);
            session.save(lastName);
            session.save(age);
            trSaveUser.commit();
        } catch (SessionException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction trRemoveUser = session.beginTransaction();
            session.delete(id);
            trRemoveUser.commit();
        } catch (SessionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
