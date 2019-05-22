package ru.job4j.io.input;

/**
 * StubInput.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 4/17/2019
 */
public class StubInput implements Input {
    /**
     * field query abstract data user.
     */
    private final String[] query;
    /**
     * field current position in array abstract data.
     */
    private int position;

    /**
     * Constructor.
     *
     * @param aQuery query
     */
    public StubInput(final String[] aQuery) {
        this.query = aQuery;
    }

    @Override
    public final String request() {
        return this.query[this.position++];
    }
}
