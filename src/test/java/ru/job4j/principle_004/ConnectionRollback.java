package ru.job4j.principle_004;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;

/**
 * Connection, which rollback all commits.
 * It is used for integration test.
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @since 21.03.2019
 */
public class ConnectionRollback {

    /**
     * Create connection with autocommit=false mode and rollback call, when conneciton is closed.
     * @param connection connection.
     * @return Connection object.
     * @throws SQLException possible exception.
     */
    public static Connection create(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        return (Connection) Proxy.newProxyInstance(
                ConnectionRollback.class.getClassLoader(),
                new Class[] {
                        Connection.class
                },
                (proxy, method, args) -> {
                    Object rsl = null;
                    if ("close".equals(method.getName())) {
                        connection.rollback();
                        connection.close();
                    } else {
                        rsl = method.invoke(connection, args);
                    }
                    return rsl;
                }
        );
    }

    /**
     * Proxy for session factory.
     * @param factory session factory.
     * @return session factory.
     */
    public static SessionFactory create(SessionFactory factory) {
        Session session = factory.openSession();
        session.beginTransaction();
        return (SessionFactory) Proxy.newProxyInstance(
                ConnectionRollback.class.getClassLoader(),
                new Class[] {
                        SessionFactory.class
                },
                (proxy, method, args) -> {
                    Object rsl = null;
                    if ("openSession".equals(method.getName())) {
                        rsl = create(session);
                    } else if ("close".equals(method.getName())) {
                        session.getTransaction().rollback();
                        session.close();
                    } else {
                        rsl = method.invoke(factory, args);
                    }
                    return rsl;
                }
        );
    }

    /**
     * Proxy for session.
     * @param session session.
     * @return session.
     */
    public static Session create(Session session) {
        return (Session) Proxy.newProxyInstance(
                ConnectionRollback.class.getClassLoader(),
                new Class[] {
                        Session.class
                },
                (proxy, method, args) -> {
                    Object rsl;
                    if ("beginTransaction".equals(method.getName())) {
                        rsl = mock(Transaction.class);
                    } else if ("close".equals(method.getName())) {
                        rsl = null;
                    } else if ("clear".equals(method.getName())) {
                        session.getTransaction().rollback();
                        rsl = null;
                    } else {
                        rsl = method.invoke(session, args);
                    }
                    return rsl;
                }
        );
    }
}
