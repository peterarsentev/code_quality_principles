package ru.job4j.principle_004;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Contains pool.
 */
public class HibernateFactory {
    /**
     * Instance of factory.
     */
    public static final SessionFactory FACTORY = new Configuration()
            .configure()
            .buildSessionFactory();

}
