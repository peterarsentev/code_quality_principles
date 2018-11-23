package ru.job4j.principle_003;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class EvenItTest {
    /**
     * Test for throwing.
     */
    @Test(expected = IllegalStateException.class)
    public void whenItEmptyThenThrowException() {
        new EvenIt(new int[] {}).iterator().next();
    }

    /**
     * Test for get even numbers.
     */
    @Test
    public void whenItEvenAndNotEvenThenGetEvent() {
        assertThat(
                new EvenIt(
                        new int[] {1, 2}
                ).iterator().next(),
                is(2)
        );
    }
}