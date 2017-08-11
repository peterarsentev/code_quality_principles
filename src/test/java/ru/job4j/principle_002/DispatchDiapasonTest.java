package ru.job4j.principle_002;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test for person permission  by age.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class DispatchDiapasonTest {

    /**
     * Between 14 and 18.
     */
    @Test
    public void whenBetween14and18ThenLimited() {
       assertThat(
               new DispatchDiapason().init().access(
                       () -> 16
               ),
               is(Person.Access.LIMIT)
       );
    }

    /**
     * Up 18 age.
     */
    @Test
    public void whenUp18AgeThenFree() {
        assertThat(
                new DispatchDiapason().init().access(
                        () -> 21
                ),
                is(Person.Access.FREE)
        );
    }

    /**
     * Under 14 age.
     */
    @Test
    public void whenLess14ThenForbidden() {
        assertThat(
                new DispatchDiapason().init().access(
                        () -> 10
                ),
                is(Person.Access.FORBIDDEN)
        );
    }
}