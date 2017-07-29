package ru.job4j.principle_001;

import java.util.Comparator;

/**
 * Good example of rewrite comparator.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class UserCompareGood implements Comparator<User> {

    /**
     * ${@inheritDoc}.
     */
    @Override
    public int compare(final User left, final User right) {
        final int rsl = left.name().compareTo(right.name());
        return rsl != 0 ? rsl : Integer.compare(left.age(), right.age());
    }
}
