package ru.job4j.principle_002;

/**
 * //TODO add comments.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class SwitchAntiPattern {

    /**
     * Sent message to type.
     * @param msg message
     * @return true if it finds a type.
     */
    public boolean sent(final Message msg) {
        final boolean rsl;
        switch (msg.type()) {
            case EMAIL:
                // sent to email.
                rsl = true;
                break;
            case JABBER:
                // sent to jabber.
                rsl = true;
                break;
            case TWITTER:
                // sent to twitter.
                rsl = true;
                break;
            default:
                // sent to etc.
                rsl = false;
                break;
        }
        return rsl;
    }
}
