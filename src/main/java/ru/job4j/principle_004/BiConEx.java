package ru.job4j.principle_004;

/**
 * Functional interface, It take two params with possible exception.
 * @param <L> first arg
 * @param <R> second arg
 */
public interface BiConEx<L, R> {
    /**
     * Accept two args.
     * @param left first.
     * @param right second.
     * @throws Exception possible exception.
     */
    void accept(L left, R right) throws Exception;
}
