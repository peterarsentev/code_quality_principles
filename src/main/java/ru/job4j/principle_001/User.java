package ru.job4j.principle_001;

/**
 * User model.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public interface User {
    /**
     * Name.
     * @return name of user.
     */
    String name();

    /**
     * Age.
     * @return age of user.
     */
    int age();
}
