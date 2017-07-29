package ru.job4j.principle_001;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test's of compared users.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class UserComparedTest {
    /**
     * Test bad code.
     */
    @Test
    public void whenLeftGreatThenMinus() {
        assertThat(
                new UserCompareBad().compare(
                        new UserPojo("Nick", 20),
                        new UserPojo("Nick", 30)
                ), is(-1));
    }

    /**
     * Test good code.
     */
    @Test
    public void whenRfcLeftGreatThenMinus() {
        assertThat(
                new UserCompareGood().compare(
                        new UserPojo("Paul", 20),
                        new UserPojo("Paul", 30)
                ), is(-1));
    }
}