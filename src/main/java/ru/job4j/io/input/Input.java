package ru.job4j.io.input;

import java.io.IOException;

/**
 * Input.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 4/17/2019
 */
public interface Input {
    /**
     * field request data from user.
     *
     * @return line data from user input
     *
     * @throws IOException exception
     */
    String request() throws IOException;
}
