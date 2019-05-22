package ru.job4j.io.input;

import java.io.IOException;

/**
 * ValidateInput.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 4/17/2019
 */
public class ValidateInput implements Input {
    /**
     * field input.
     */
    private final Input input;

    /**
     * Constructor.
     *
     * @param aInput input
     */
    public ValidateInput(final Input aInput) {
        this.input = aInput;
    }

    @Override
    public final String request() throws IOException {
        return this.input.request();
    }
}
