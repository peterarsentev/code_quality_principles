package ru.job4j.io.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ConsoleInput.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 4/17/2019
 */
public class ConsoleInput implements Input {
    /**
     * field reader data user.
     */
    private final BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

    @Override
    public final String request() throws IOException {
        return this.reader.readLine();
    }
}
