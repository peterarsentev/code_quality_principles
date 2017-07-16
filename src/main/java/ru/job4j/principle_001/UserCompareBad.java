package ru.job4j.principle_001;

import java.util.Comparator;

/**
 * Bad example.
 *
 * List of problems
 * 1. Multi returns.
 * 2. Multi conditions (if-else-if).
 * 3. Unnecessary auto-boxing.
 * 4. Unnecessary auto-boxing.
 * 5. Explicitly return result.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class UserCompareBad implements Comparator<User> {

    /**
     * ${@inheritDoc}
     */
    @Override
    public int compare(User left, User right) {
        if (left.name().compareTo(right.name()) == 1) {
            return 1;
        } else if (left.name().compareTo(right.name()) == -1) {
            return -1;
        } else if (Integer.valueOf(left.age()) > Integer.valueOf(right.age()) &&
                left.name().compareTo(right.name()) == 0) {
            return 1;
        } else if (
                Integer.valueOf(left.age()) < Integer.valueOf(right.age()) &&
                        left.name().compareTo(right.name()) == 0
                ) {
            return -1;
        } else {
            return 0;
        }
    }
}

