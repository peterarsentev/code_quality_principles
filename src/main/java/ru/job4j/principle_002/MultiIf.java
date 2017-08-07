package ru.job4j.principle_002;

import ru.job4j.principle_002.Message.Type;

/**
 * Anti-pattern - multi if statements.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class MultiIf {

    /**
     * Sent message to type.
     * @param msg message
     * @return true if it finds a type.
     */
    public boolean sent(final Message msg) {
        boolean rsl = false;
        if (msg.type() == Type.EMAIL) {
            // sent to email.
            rsl = true;
        } else if (msg.type() == Type.JABBER) {
            // sent to jabber.
            rsl = true;
        } else if (msg.type() == Type.TWITTER) {
            // sent to twitter.
            rsl = true;
        } else if (msg.type() == Type.ETC) {
            // sent to etc.
            rsl = true;
        }
        return rsl;
    }
}
