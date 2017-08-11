package ru.job4j.principle_002;

/**
 * Person.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public interface Person {
    /**
     * Person's age.
     * @return age.
     */
    int age();

    /**
     *  Access type.
     */
    enum Access {
        /**
         * Forbidden.
         */
        FORBIDDEN,
        /**
         * Limited.
         */
        LIMIT,
        /**
         * Free access.
         */
        FREE
    }
}
