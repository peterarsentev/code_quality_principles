package ru.job4j.principle_004;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Function;

/**
 * Impl of store by hibernate.
 */
public class HibernateStore implements Store<User> {
    /**
     * Session Factory.
     */
    private final SessionFactory factory;

    /**
     * Constructor.
     * @param factory Session factory.
     */
    public HibernateStore(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Wrapper for transactions.
     * @param command actions.
     * @param <T> model type.
     * @return result of actions.
     */
    private <T> T tx(final Function<Session, T> command) {
        final Session session = factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public User add(User model) {
        tx(session -> session.save(model));
        return model;
    }

    @Override
    public void update(User model) {
        tx(
                session ->  {
                    session.update(model);
                    return null;
                }
        );
    }

    @Override
    public void delete(int id) {
        tx(
                session ->  {
                    session.delete(new User(id, null));
                    return null;
                }
        );
    }

    @Override
    public List<User> findAll() {
        return tx(session -> session.createQuery("from User").list());
    }

    @Override
    public User findById(int id) {
        return tx(session -> session.find(User.class, id));
    }
}
