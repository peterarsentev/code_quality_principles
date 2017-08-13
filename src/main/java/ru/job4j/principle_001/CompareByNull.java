package ru.job4j.principle_001;

import java.util.Comparator;
import java.util.function.BiFunction;

/**
 * Compare by null.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class CompareByNull implements Comparator<User> {

    /**
     * ${@inheritDoc}.
     */
    @Override
    public int compare(User left, User right) {
        return compareIfNull(left, right, (lft, rht) -> Integer.compare(lft.age(), rht.age()));
    }


    /**
     * Compare values by null.
     * @param left left value.
     * @param right right value.
     * @param compare if left and right not null then compare by function.
     * @return 1, 0, -1.
     */
    public int compareIfNull(User left, User right, BiFunction<User, User, Integer> compare) {
        int rsl;
        if (left != null && right != null) {
            rsl = compare.apply(left, right);
        } else if (left != null) {
            rsl = -1;
        } else if (right != null) {
            rsl = 1;
        } else {
            rsl = 0;
        }
        return rsl;
    }
}
