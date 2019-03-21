package ru.job4j.principle_004;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static ru.job4j.principle_004.ConnectionRollback.create;

/**
 * Tests for hibernate.
 */
public class HibernateStoreTest {
    /**
     * Create a new user and check id.
     */
    @Test
    public void whenCreateUser() {
        SessionFactory factory = create(HibernateFactory.FACTORY);
        HibernateStore store = new HibernateStore(factory);
        User user = store.add(new User(-1, "Petr Arsentev"));
        assertThat(
                user.getId(),
                not(-1)
        );
        factory.close();
    }

    /**
     * Create a new user and find it.
     */
    @Test
    public void whenCreateAndFind() {
        SessionFactory factory = create(HibernateFactory.FACTORY);
        Session session = factory.openSession();
        HibernateStore store = new HibernateStore(factory);
        User user = store.add(new User(-1, "Petr Arsentev"));
        assertThat(
                store.findById(user.getId()).getLogin(),
                is("Petr Arsentev")
        );
        session.clear();
        assertThat(store.findAll().isEmpty(), is(true));
        factory.close();
    }

    /**
     * Clean tests data.
     */
    @AfterClass
    public static void close() {
        HibernateFactory.FACTORY.close();
    }
}