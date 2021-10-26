package ru.job4j.principle_007;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Даны две строки. Нужно проверить, что вторая строка получилась методом перестановок символов в первой строке.
 */
public class ContainsAllTest {
    @Test
    public void whenEq() {
        assertThat(ContainsAll.check("Hello", "Hlloe"), is(true));
    }

    @Test
    public void whenNotEq() {
        assertThat(ContainsAll.check("Hello", "Halle"), is(false));
    }

    @Test
    public void whenNotMultiEq() {
        assertThat(ContainsAll.check("heloo", "hello"), is(false));
    }
}