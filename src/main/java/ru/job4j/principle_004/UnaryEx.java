package ru.job4j.principle_004;

/**
 * Unary functional interface with exception.
 */
public interface UnaryEx {
    /**
     * Action with exception.
     * @throws Exception possible exception.
     */
    void action() throws Exception;
}
