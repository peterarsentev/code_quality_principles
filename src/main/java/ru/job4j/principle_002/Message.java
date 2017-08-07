package ru.job4j.principle_002;

/**
 * //TODO add comments.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public interface Message {

    /**
     * Sent to type.
     * @return type.
     */
    Type type();

    /**
     * Dist's type.
     */
    enum Type {
        /**
         * Email.
         */
        EMAIL,
        /**
         * Jabber.
         */
        JABBER,
        /**
         * Twitter.
         */
        TWITTER,
        /**
         * Etc.
         */
        ETC,
        /**
         * Unknown.
         */
        UNKNOWN
    }
}
