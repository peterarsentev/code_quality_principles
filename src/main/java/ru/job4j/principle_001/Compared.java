package ru.job4j.principle_001;

/**
 * Simple example of multiple return statements.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Compared {
    /**
     * Return mex of left and right.
     * @param left left.
     * @param right right.
     * @return max of left, right.
     */
    int maxBad(int left, int right) {
        if (left > right) {
            return left;
        } else {
            return right;
        }
    }

    /**
     * Return mex of left and right.
     * @param left left.
     * @param right right.
     * @return max of left, right.
     */
    int maxGood(int left, int right) {
        return left > right ? left : right;
    }
}
